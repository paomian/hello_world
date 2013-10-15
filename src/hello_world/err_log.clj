(ns hello_world.err_log
  (:use
    [hiccup core page]
    [hiccup.form]
    [hello_world.template           :only [template]]))
(template err_log
          [:div.container 
           [:p.text-warning {:style "text-align:center"} "用户名或密码错误，请确认如果你"
            [:a {:href "reset"} "忘记密码"]]
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
              [:td [:button.btn.btn-primary {:type "submit"} "Log In"]]]]]])
