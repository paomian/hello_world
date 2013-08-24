(ns hello_world.login
  (:use compojure.core))
(defn dologin [user pwd]
  (if (and (= user "test") (= pwd "1234"))
    (str "You are login success")
    (str "Error!You user is " user pwd)))
