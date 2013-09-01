(ns hello_world.mongo
  (:use [monger.core                 :only [connect! connect set-db! get-db]]
        [noir.session                :only [wrap-noir-session]]
        [monger.ring.session-store   :only [session-store]]))
(defn prepare-mongo []
  (do
    (connect!)
    (set-db! (get-db "hello"))
    (wrap-noir-session (session-store "session"))))
