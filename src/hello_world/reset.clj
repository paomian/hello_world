(ns hello_world.reset
  (:use
   [monger.collection            :only [find-one-as-map update]]
   [hello_world.template         :only [template]]
   [monger.operators]
   [hiccup.form])
  (:import [org.jasypt.util.password StrongPasswordEncryptor])
  (:require
   [ring.util.response           :as response]
   [noir.session                 :as session]))
(defn doreset [user pwd npwd]
  (let [result (find-one-as-map "user" {:user user})]
    (if result 
      (if (.checkPassword (StrongPasswordEncryptor.) pwd (result :pwd))
        (do (update "user" {:user user} {$set {:pwd (.encryptPassword (StrongPasswordEncryptor.) npwd)}})
            (session/clear!)
            (response/redirect "/login"))
        (response/redirect "/"))
      (response/redirect "/"))))

(template reset-page  
          [:div.container 
           [:form.form-signin {:method "POST" :action "/reset"}
            [:table
             [:tr
              [:td (label :user "Username")]
              [:td (text-field :user)]]
             [:tr
              [:td (label :pwd "Password")]
              [:td (password-field  :pwd)]]
             [:tr
              [:td (label :npwd "NewPassword")]
              [:td (password-field  :npwd)]]
             [:tr
              [:td]
              [:td [:button.btn.btn-primary {:type "submit"} "Reset"]]]]]])





