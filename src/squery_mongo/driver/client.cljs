(ns squery-mongo.driver.client
  (:require clojure.string
            [squery-mongo.driver.settings :refer [defaults]]))

(def mongodb (js/require "mongodb"))

(defn create-mongo-client
  ([]
   (let [MongoClient (.-MongoClient mongodb)]
     (MongoClient. (defaults :connection-string) (clj->js {"useUnifiedTopology" true}))))
  ([connection-string]
   (let [MongoClient (.-MongoClient mongodb)]
     (MongoClient. connection-string (clj->js {"useUnifiedTopology" true}))))
  ([connection-string options]
   (let [MongoClient (.-MongoClient mongodb)]
     (MongoClient. connection-string  (aset (clj->js options) "useUnifiedTopology" true)))))

#_(def mongodb (js/require "mongodb"))

#_(def mongoClientType (let [MongoClient (.-MongoClient mongodb)]
                       (MongoClient.)))

#_(def mongo-client (atom nil))

#_(defn set-mongo-client [new-mongo-client]
  (reset! mongo-client new-mongo-client))

#_(defn get-mongo-client []
    @mongo-client)

;;mongodb://[user:pass@]host1:port1,host2:port2.../?option1&option2.....
;;default port= 27017 (if i dont add it )
;;Example "mongodb://user1:pwd1@host1/?authSource=db1&authMechanism=SCRAM-SHA-256&ssl=true"
;;https://docs.mongodb.com/manual/reference/connection-string/
#_(defn connection-string [options]
    (let [username (get options :username)
          password (get options :password)
          hosts (if (get options :hosts)
                  (if (vector? (get options :hosts))
                    (let [hosts-vec (get options :host)]
                      (clojure.string/join "," hosts-vec))
                    (get options :hosts))
                  (get options :host))
          hosts (if hosts hosts "localhost:27017")
          authSource (get options :authSource)
          authMechanism (get options :authMechanism "SCRAM-SHA-256")
          ssl (get options :ssl)
          connection-string (str "mongodb://"
                                 (if username (str username ":" password "@") "")
                                 (if hosts (str hosts "/?") "")
                                 (if authSource (str "authSource=" authSource "&") "")
                                 (if (and username authMechanism) (str "authMechanism=" authMechanism "&") "")
                                 (if ssl (str "ssl=" ssl "&") ""))
          ;- (prn connection-string)
          ]
      connection-string))





#_(defn connect
    ([] (connect {}))     ;;default=localhost port=27017
    ([options]
     (if (map? options)
       (let [connection-string (connection-string options)]
         (.connect (create-mongo-client connection-string))
         ;(set-mongo-client (create-mongo-client connection-string))
         )
       (let [connection-string options]
         (.connect (create-mongo-client connection-string))
         ;(set-mongo-client (create-mongo-client connection-string))
         ))))



#_(defn disconnect []
  (.close ^MongoClient @mongo-client))


;;--------------------------------db-namespaces------------------------------------------------------------------------

(defn get-database [connected-mongo-client db-name]
  (.db connected-mongo-client db-name))

(defn get-collection [db coll-view-name]
  (.collection db coll-view-name))


;;-------------------------MongoClient arguments------------------------------------------------------------------------

(defn get-js-connection [args]
  (reduce (fn [[js-connection args] arg]
            (if (and (map? arg) (contains? arg :client))
              [arg args]
              [js-connection (conj args arg)]))
          [nil []]
          args))