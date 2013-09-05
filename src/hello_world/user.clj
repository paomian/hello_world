(ns hello_world.user
  (:use
   [monger.collection    :only [insert insert-batch find-one-as-map]]
   [hello_world.template :only [template]]
   [hiccup core page]
   )
  (:require [noir.session     :as session])
  (:import [org.bson.types ObjectId]
           [com.mongodb DB WriteConcern]))
;;(defn info [user]
;;(let [result (session/get :user)]
;;(if result (template (str (:user result))))))
#_(defn info [user]
    (let [result (find-one-as-map "user" {:user user})]
      (if result
        (if (= user (session/get :user))
          (for [x (keys result)]
            (str x "   " (x result)))
          (list
           (str "user" (:user result))
           (str "email" (:email result))))
        (str user))))
(defn info [user]
  (let [result (find-one-as-map "user" {:user user})
        user (session/get :user)]
    (html5
     [:head
      [:title "info"]
      (include-css "http://libs.baidu.com/bootstrap/2.3.2/css/bootstrap.min.css")
      (include-css "http://libs.baidu.com/bootstrap/2.3.2/css/bootstrap-responsive.min.css")
      (include-css "bootstrap/css/mycss.css")
      ]
     [:body
      [:div.navbar.navbar-inverse 
       [:div.navbar-inner 
        [:div.container 
         [:a.btn.btn-navbar {:data-toggle "collapse" :data-target ".nav-collapse"}
          (for [x (range 4)] 
            [:span.icon-bar])]
         [:a.brand {:href "/"} "Paomian"]
         [:ul.nav 
          [:li [:a {:href "/"} "Home"]]
          [:li [:a {:href "#"} "Link"]]
          [:li [:a {:href "#"} "some"]]
          ]
         [:form.navbar-search.pull-left [:input.search-query.span2 {:type "text" :placeholder "Search"}]]
         [:div.nav-collapse.collapse
          [:div.btn-group.pull-right
           [:button.btn.dropdown-toggle.btn-primary {:data-toggle "dropdown" :href "#"} (if user (str user) "用户")
            [:span.caret]]
           (if user 
             [:ul.dropdown-menu
              [:li [:a {:href (str "/user/" user)} "个人信息"]]
              [:li [:a {:href "/logout"} "注销"]]]
             [:ul.dropdown-menu
              [:li [:a {:href "/login"} "登陆"]]
              [:li [:a {:href "/register"} "注册"]]])
           ]]]]]
      (if result
        (if (= user (:user result))
          [:table
           [:tr
            [:td "last-login"]
            [:td (str (:last-login result))]]
           [:tr
            [:td "register-time"]
            [:td (str (:register-time result))]]]
          [:table
           [:tr
            [:td "user"]
            [:td (str (:user result))]]
           [:tr
            [:td "Email"]
            [:td (str (:email result))]]]))
      (include-js "http://libs.baidu.com/jquery/2.0.2/jquery.min.js")
      (include-js "http://libs.baidu.com/bootstrap/2.3.2/js/bootstrap.min.js")])))
