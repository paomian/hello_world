(ns hello_world.user
  (:use [monger.core         :only [connect! connect set-db! get-db]]
        [monger.collection   :only [insert insert-batch]
        ])
  (:import [org.bson.types ObjectId]
           [com.mongodb DB WriteConcern]))
(connect!)
(set-db! (monger.core/get-db "hello"))
(println "This is a test")
(insert "user" { :_id (ObjectId.) :first_name "sha" :last_name "bi" })
