(ns hello_world.handler
  (:use compojure.core
        [hello_world.err_log         :only [err_log]]
        [hello_world.login           :only [dologin]]
        [hello_world.index           :only [index]]
        [hello_world.register        :only [doregister register]]
        [hiccup.middleware           :only (wrap-base-url)])
  (:require [compojure.handler       :as handler]
            [compojure.route         :as route]))

(defroutes app-routes
  (POST "/login" [user pwd]
        (dologin user pwd))
  (POST "/register" [user pwd r-pwd email]
        (doregister user pwd r-pwd email))
  (GET "/register" [] (register))
  (GET "/" [] (index))
  (GET "/err-log" [] (err_log))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/site app-routes)
      (wrap-base-url)))
