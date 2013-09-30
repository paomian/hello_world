(ns hello_world.chat
  (:use org.httpkit.server
        [hello_world.template                :only [template]]
        [clojure.data.json                   :only [json-str read-json]]
        [hiccup.form]
        [hiccup.page])
  (:require
    [noir.session                           :as session]
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
                         :author "system"}]))
(defn mesg-received [msg]
  (let [data (read-json msg)]
    (when (:msg data)
      (let [data (merge data {:time (now) :id (next-id) :author (session/get :user)})]
        (dosync
          (let [all-msgs* (conj @all-msgs data)
                total (count all-msgs*)]
            (if (> total 100)
              (ref-set all-msgs (vec (drop (- total 100) all-msgs*)))
              (ref-set all-msgs all-msgs*))))))
    (doseq [client (keys @clients)]
      ;; send all, client will filter them
      (send! client (json-str @all-msgs)))))
(defn chat [req]
  (with-channel req channel
                (swap! clients assoc channel true)
                (on-receive channel #'mesg-received)
                (on-close channel (fn [status]
                                    (swap! clients dissoc channel)))))
#_(run-server chat {:port 9090})
#_(template chat-page
            (list
              (text-field :input)
              (include-js "bootstarp/js/ws.js")))
