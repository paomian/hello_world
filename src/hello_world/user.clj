(ns hello_world.user
  (:use [monger.core          :only [connect! connect set-db! get-db]]
        [monger.collection    :only [insert insert-batch find-one-as-map]
        [hello_world.template :only [template]]
        ])
  (:require [noir.session     :as session])
  (:import [org.bson.types ObjectId]
           [com.mongodb DB WriteConcern]))
;;(defn info [user]
  ;;(let [result (session/get :user)]
    ;;(if result (template (str (:user result))))))
