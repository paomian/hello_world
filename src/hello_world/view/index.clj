(ns hello_world.view.index
	(:use [hiccup core page]))

(defn index-page []
	(html5
		[:head
		   [:title "Hello World"]
		   (include-css "/css/mycss.css")]
	    [:body
	    	[:h1 [:center "Hello World!"]]]))