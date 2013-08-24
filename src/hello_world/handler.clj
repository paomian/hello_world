(ns hello_world.handler
  (:use compojure.core
        [hello_world.view.template   :only [template]]
        [hello_world.login           :only [dologin]]
        [hello_world.index           :only [index]]
        [hiccup.middleware           :only (wrap-base-url)])
  (:require [compojure.handler       :as handler]
            [compojure.route         :as route]))

(defroutes app-routes
  (POST "/login" [user pwd]
        (dologin user pwd))
  (GET "/" [] (index))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/site app-routes)
      (wrap-base-url)))
