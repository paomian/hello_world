(defproject hello_world "0.1.0-SNAPSHOT"
  :description "A test for clojure learning"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
  				 [hiccup "1.0.4"]
                 [compojure "1.1.5"]
                 [hickory "0.5.0"]
                 [com.novemberain/monger "1.5.0"]
                 [ring "1.2.0"]
                 [org.jasypt/jasypt "1.7"]
                 [lib-noir "0.6.6"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler hello_world.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]]}})
