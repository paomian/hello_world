(ns hello_world.login
  (:use compojure.core
        [monger.core             :only [connect! set-db! get-db]]
        [monger.collection       :only [find-one-as-map update]]
        [monger.operators]
        [hello_world.template    :only [template]]
        [hiccup.form])
  (:import [org.jasypt.util.password StrongPasswordEncryptor])
  (:require 
   [ring.util.response     :as response]))
(connect!)
(set-db! (get-db "hello"))
(defn dologin [user pwd]
  (let [result (find-one-as-map "user" {:user user})]
    (if result (if (.checkPassword (StrongPasswordEncryptor.) pwd  (result :pwd))
                 (do (update "user" {:user user}  {$set {:last-login (java.util.Date.)}})   
                     (response/redirect "/"))
                 (response/redirect "/err-log"))
        (response/redirect "/err-log"))))
(template login-page
          `[:div.container 
            [:form.form-signin {:method "POST" :action "/login"}
             [:table
              [:tr
               [:td ~(label :user "Username")]
               [:td ~(text-field :user)]]
              [:tr
               [:td ~(label :pwd "Password")]
               [:td ~(password-field  :pwd)]]
              [:tr
               [:td]
               [:td [:button.btn.btn-primary {:type "submit"} "Log In"]]]]]])
