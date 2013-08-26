(ns hello_world.template
  (:use [hiccup core page]
        [hiccup.form]))

(defmacro template [page-name & [code]]
  `(defn ~page-name []
     (html5
      [:head
       [:title "Hello World"]
       (include-css "bootstrap/css/bootstrap.min.css")
       (include-css "bootstrap/css/bootstrap-responsive.min.css")
       (include-css "bootstrap/css/mycss.css")
       ]
      [:body
       [:div.navbar.navbar-inverse 
        [:div.navbar-inner 
         [:div.container 
          [:a.btn.btn-navbar {:data-toggle "collapse" :data-target ".nav-collapse"}
           (for [x# (range 4)] 
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
            [:button.btn.dropdown-toggle.btn-primary {:data-toggle "dropdown" :href "#"} "  用户  "
             [:span.caret]]
            [:ul.dropdown-menu
             [:li [:a {:href "#"} "登陆"]]
             [:li [:a {:href "/register"} "注册"]]]]]]]]
       ~code
       (include-js "bootstrap/js/jquery-2.0.2.min.js")
       (include-js "bootstrap/js/bootstrap.min.js")
       ])))
