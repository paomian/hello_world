(ns hello_world.register
  (:import [org.jasypt.util.password StrongPasswordEncryptor]
          [org.bson.types ObjectId]
          [com.mongodb DB WriteConcern])
  (:use
   [monger.core             :only [connect connect! set-db! get-db]]
   [monger.collection       :only [insert insert-batch]]))
(connect!)
(set-db! (get-db "hello"))
(defn doregister
  [user pwd email]
  
  )
