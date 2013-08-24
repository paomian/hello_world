(ns hello_world.index
  (:use
   [hiccup core page]
   [hiccup.form]
   [hello_world.template         :only [template]]))
(template index
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
