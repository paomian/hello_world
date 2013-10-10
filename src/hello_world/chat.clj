(ns hello_world.chat
  (:use org.httpkit.server
        [hello_world.template                :only [template]]
        [hello_world.login                   :only [check-user show-userlist]]
        [clojure.data.json                   :only [json-str read-json]]
        [hiccup.form]
        [hiccup.page])
  (:require
    [noir.session                           :as session]
    [noir.cookies                            :as cookies]
    [ring.util.response                     :as response]))
(template chat-page
          (list
            [:div.container {:id "mydiv"}
             (text-field :input)]
            (include-js "bootstrap/js/ws.js")))
(defn chat-check []
  (let [user (session/get :user)]
    (if user
      (chat-page)
      (response/redirect "/login"))))
(defn- now [] (quot (System/currentTimeMillis) 1000))
(def clients (atom {}))
(let [max-id (atom 0)]
  (defn next-id []
    (swap! max-id inc)))
(defonce all-msgs (ref [{:id (next-id),        ; all message, in a list
                         :time (now)
                         :msg "this is a live chatroom, have fun",
                         :user "system"}]))
(defn mesg-received [msg]
  (let [data (read-json msg)]
    (when (:msg data)
      (let [data (merge data {:time (now) :id (next-id)})]
        (dosync
          (let [all-msgs* (conj @all-msgs data)
                total (count all-msgs*)]
            (if (> total 100)
              (ref-set all-msgs (vec (drop (- total 100) all-msgs*)))
              (ref-set all-msgs all-msgs*))))))
    (doseq [client (keys @clients)]
      ;; send all, client will filter them
      (if (:msg data)
        (list
          (send! client (json-str data))
          (println data))
        (if (:now data)
          (send! client (json-str {:statu "Heartbeat success"})))))))
(defn chat [req]
  (show-userlist)
  (println "请求连接用户：" (:value (get (:cookies req) "user")))
  (if 
    (check-user (:value (get (:cookies req) "user")))
    (with-channel req channel
                  (swap! clients assoc channel true)
                  (println "clients" @clients)
                  (on-receive channel #'mesg-received)
                  (on-close channel (fn [status]
                                      (swap! clients dissoc channel)
                                      (println channel "closed,status" status)))
                  )))
