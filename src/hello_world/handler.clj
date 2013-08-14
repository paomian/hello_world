(ns hello_world.handler
  (:use compojure.core
  	    hello_world.view.template
  	    [hiccup.middleware :only (wrap-base-url)])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/" [] (template))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/site app-routes)
  	  (wrap-base-url)))
