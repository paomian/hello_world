(ns hello_world.index
  (:use
    [hiccup core page]
    [hiccup.form]
    [hello_world.template         :only [template]])
  (:require [noir.session         :as session]))
(template index
          [:div.container-narrow
           [:hr]
           [:div.jumbotron 
            [:h1 "一个还没有投入运营的时时聊天的平台，完全运用web服务，只要一个可以用的浏览浏览器"]
            [:p.lead "其实也没啥，现在就尼玛有个登陆注册，其他的什么都没有"]
            [:p.lead "终于在2013年的某一天有了聊天功能。。。"]
            (let [user (session/get :user)]
              (if user 
                [:a.btn.btn-large.btn-danger {:href "/chat"} "聊天"]
                [:a.btn.btn-large.btn-danger {:href "/login"} "登陆"]))]])
