(ns hello_world.user 
  (:use
    [monger.collection    :only [find-one-as-map update]]
    [hello_world.template :only [template]]
    [monger.operators]
    [hiccup core page]
    [hiccup.form]
    )
  (:require [noir.session         :as session]
            [ring.util.response   :as response])
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
           [:li [:a {:href "/"} "主页"]]
           [:li [:a {:href "#"} "技术支持"]]
           [:li [:a {:href "#"} "got it"]]
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
           [:div.container
            [:form {:method "POST" :action "/change"}
             [:table
              (for [[name label-name & [nchange]]
                    [["邮箱" :email true]
                     ["用户" :user true]
                     ["昵称" :nickname]
                     ["豆瓣" :douban]
                     ["微博" :weibo]]]
                [:tr
                 [:td (label label-name name)]
                 [:td (text-field (if nchange {:disabled "disabled"} {}) label-name (str (label-name result)))]])
              [:tr
               [:td (label :geren "个人说明")]
               [:td (text-area :geren (str (:geren result)))]]
              [:tr
               [:td]
               [:td [:button.btn.btn-primary {:type "submit"} "保存"]]]]]]
           [:div.container
            [:table
             (for [[name label-name]
                   [["昵称 :" :user]
                    ["邮箱 :" :email]]]
               [:tr
                [:td (label label-name name)]
                [:td (str (label-name result))]])]]))
       (include-js "http://libs.baidu.com/jquery/2.0.2/jquery.min.js")
       (include-js "http://libs.baidu.com/bootstrap/2.3.2/js/bootstrap.min.js")])))
(defn dochange "doc-string" [nickname douban weibo geren]
  (let [suser (session/get :user)]
    (do
      (update "user" {:user suser} {$set {:nickname nickname :douban douban :weibo weibo :geren geren}})
      (response/redirect (str "/user/" suser)))))
