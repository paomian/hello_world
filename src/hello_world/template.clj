(ns hello_world.template
  (:use [hiccup core page]
        [hiccup.form]
        [monger.collection             :only [find-one-as-map]])
  (:require [noir.session              :as session]))

(defn html-temp [code]
  (let [user (session/get :user)]
    (html5
     [:head
      [:title "Hello World"]
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
              [:li [:a {:href "/login/reset"} "密码重置"]]
              [:li [:a {:href "/logout"} "注销"]]]
             [:ul.dropdown-menu
              [:li [:a {:href "/login"} "登陆"]]
              [:li [:a {:href "/register"} "注册"]]])
           ]]]]]
      code
      (include-js "http://libs.baidu.com/jquery/2.0.2/jquery.min.js")
      (include-js "http://libs.baidu.com/bootstrap/2.3.2/js/bootstrap.min.js")
      ])))
(defmacro template [page-name & [code]]
  `(defn ~page-name []
     (html-temp ~code)))
