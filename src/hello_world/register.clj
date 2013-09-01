(ns hello_world.register
  (:import [org.jasypt.util.password StrongPasswordEncryptor]
           [org.bson.types ObjectId]
           [com.mongodb DB WriteConcern])
  (:require
   [noir.session            :as session]
   [ring.util.response      :as response])
  (:use
   [hiccup core page]
   [hiccup.form]
   [monger.core             :only [connect connect! set-db! get-db]]
   [monger.collection       :only [insert insert-batch]]
   [hello_world.template    :only [template]]))
(connect!)
(set-db! (get-db "hello"))
(defn doregister
  [user pwd r-pwd email]
  (if (and (= pwd r-pwd)
           ()) ;;验证重名问题
    (do 
      (insert "user" {:_id (ObjectId.) :user user :pwd (.encryptPassword (StrongPasswordEncryptor.) pwd) :email email :register-time (java.util.Date.)})
      (response/redirect "/")
    )
    ))
(template register
          [:div.container 
            [:form.form-signin {:method "POST" :action "/register"}
             [:table
              [:tr
               [:td (label :user "Username")]
               [:td (text-field :user)]]
              [:tr
               [:td (label :pwd "Password")]
               [:td (password-field  :pwd)]]
              [:tr
               [:td (label :r-pwd "Repeat Password")]
               [:td (password-field :r-pwd)]]
              [:tr
               [:td (label :email "Email")]
               [:td (text-field :email)]]
              [:tr
               [:td]
               [:td [:button.btn.btn-primary {:type "submit"} "Register"]]]]]])
