(defproject hello_world "0.1.0-SNAPSHOT"
  :description "A test for clojure learning"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
  				 [hiccup "1.0.4"]
                 [compojure "1.1.5"]
                 [hickory "0.5.0"]
                 [com.novemberain/monger "1.5.0"]
                 [ring/ring-core "1.2.0"]
                 [org.jasypt/jasypt "1.7"]
                 [http-kit "2.1.10"]
                 [lib-noir "0.6.6"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler hello_world.handler/app
         :init hello_world.mongo/prepare-mongo}
  :main hello_world.handler
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]]}})
