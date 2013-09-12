(ns hello_world.handler
  (:use compojure.core
        [hello_world.err_log         :only [err_log]]
        [hello_world.login           :only [dologin login-page dologout]]
        [hello_world.index           :only [index]]
        [hello_world.register        :only [doregister register]]
        [hello_world.user            :only [info dochange]]
        [hello_world.reset           :only [reset-page doreset]]
        [hiccup.middleware           :only [wrap-base-url]]
        [org.httpkit.server          :only [run-server]])
  (:require [compojure.handler       :as handler]
            [compojure.route         :as route]
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
  (POST "/change" [email user douban weibo geren]
        (dochange douban weibo geren))
  (GET "/" [] (index))
  (GET "/err-log" [] (err_log))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/site app-routes)
      (session/wrap-noir-session)
      ;;(cookies/wrap-noir-cookies)
      (wrap-base-url)))
(defn -main []
  (run-server app {:port 8080}))
