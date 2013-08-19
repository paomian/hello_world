(ns hello_world.handler
  (:use compojure.core
  	    hello_world.view.template
  	    [hiccup.middleware :only (wrap-base-url)])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/" [] (template))
  (GET "/user/:id" [id]
       (str "<h1>Hello user " id "</h1>"))
  (GET "/:foo" [foo]
       (str "Foo = " foo))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/site app-routes)
  	  (wrap-base-url)))
