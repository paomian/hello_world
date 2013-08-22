(ns hello_world.view.template
  (:use [hiccup core page]
        [hiccup.form]))

(defn template []
  (html5
    [:head
     [:title "Hello World"]
     (include-css "http://libs.baidu.com/bootstrap/2.3.2/css/bootstrap.min.css")
     (include-css "bootstrap/css/bootstrap-responsive.min.css")
     (include-css "bootstrap/css/mycss.css")
     ]
    [:body
     [:div.navbar.navbar-inverse 
      [:div.navbar-inner 
       [:div.container 
        [:a.btn.btn-navbar {:data-toggle "collapse" :data-target ".nav-collapse"}
         (for [x (range 4)]
           [:span.icon-bar])]
        [:a.brand {:href "#"} "Porject name"]
        [:ul.nav 
         [:li [:a {:href "#"} "Home"]]
         [:li [:a {:href "#"} "Link"]]
         [:li [:a {:href "#"} "some"]]
         ]
        [:form.navbar-search.pull-left [:input.search-query.span2 {:type "text" :placeholder "Search"}]]
        [:div.nav-collapse.collapse]]]]
     [:div.container 
      [:form.form-signin {:method "POST" :action "/user"}
       [:table
        [:tr
         [:td (label :user "Username")]
         [:td (text-field :user)]]
        [:tr
         [:td (label :pwd "Password")]
         [:td (password-field  :pwd)]]
        [:tr
         [:td]
         [:td [:button.btn.btn-primary {:type "submit"} "Log In"]]]]]]
     (include-js "http://libs.baidu.com/jquery/2.0.0/jquery.js")
     (include-js "http://libs.baidu.com/bootstrap/2.0.4/js/bootstrap.min.js")
     ]))
