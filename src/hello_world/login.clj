(ns hello_world.login
  (:use [compojure.core]
        [monger.operators]
        [monger.collection       :only [find-one-as-map update]]
        [hello_world.template    :only [template]]
        [hiccup.form])
  (:import [org.jasypt.util.password StrongPasswordEncryptor])
  (:require 
   [ring.util.response           :as response]
   [noir.session                 :as session]))
(def userlist #{})
(defn dologin [user pwd]
  (let [result (find-one-as-map "user" {:user user})]
    (if result (if (.checkPassword (StrongPasswordEncryptor.) pwd  (result :pwd))
                 (do (update "user" {:user user}  {$set {:last-login (java.util.Date.)}})
                     (session/put! :user user)
                     #_(def userlist (conj userlist (:user result)))
                     (response/redirect "/"))
        (response/redirect "/err-log"))
        (response/redirect "/err-log"))))
(defn dologout []
  (do
    (def userlist (disj userlist (session/get :user)))
    (session/clear!)
    (response/redirect "/")))
(template login-page  
          [:div.container 
           [:form.form-signin {:method "POST" :action "/login"}
            [:table
             [:tr
              [:td (label :user "Username")]
              [:td (text-field :user)]]
             [:tr
              [:td (label :pwd "Password")]
              [:td (password-field  :pwd)]]
             [:tr
              [:td]
              [:td [:button.btn.btn-primary {:type "submit"} "Log In"]]]
             [:tr
              [:td]
              [:td
               [:a {:href "/login/reset"} "密码重置"]]]]]])
