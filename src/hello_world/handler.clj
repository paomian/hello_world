(ns hello_world.handler
  (:use compojure.core
        [hello_world.err_log         :only [err_log]]
        [hello_world.login           :only [dologin login-page dologout]]
        [hello_world.index           :only [index]]
        [hello_world.register        :only [doregister register]]
        [hello_world.user            :only [info dochange]]
        [hello_world.reset           :only [reset-page doreset]]
        [hello_world.chat            :only [chat chat-page chat-check]]
        [hello_world.mongo           :only [prepare-mongo]]
        [hiccup.middleware           :only [wrap-base-url]]
        [org.httpkit.server          :only [run-server]])
  (:require [compojure.handler       :as handler]
            [compojure.route         :as route]
            [ring.middleware.reload  :as reload]
            [noir.session            :as session]
            [noir.cookies            :as cookies]))

(defroutes app-routes
  (POST "/login" [user pwd]
        (dologin user pwd))
  (GET "/login" [] (login-page))
  (GET "/logout" [] (dologout))
;;reset
  (GET "/login/reset" [] (reset-page))
  (POST "/reset" [user pwd npwd]
        (doreset user pwd npwd))
;;register
  (POST "/register" [user pwd r-pwd email]
        (doregister user pwd r-pwd email))
  (GET "/register" [] (register))
  (GET "/user/:user" [user] (info user))
;;ws
  (GET "/ws" [] chat)
  (GET "/chat" [] (chat-check))
  (POST "/change" [email user douban weibo geren]
        (dochange douban weibo geren))
  (GET "/" [] (index))
  (GET "/err-log" [] (err_log))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/site app-routes)
    (session/wrap-noir-session)
    (cookies/wrap-noir-cookies)))
(def in-dev? true)
(defn -main [& args]
  (let [handler (if in-dev?
                  (reload/wrap-reload app) ;; only reload when dev
                  app)]
    (do
      (prepare-mongo)
      (run-server handler {:port 8080}))))
