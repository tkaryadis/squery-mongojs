(ns squery-mongojs.driver.client
  (:require clojure.string
            [squery-mongojs.driver.settings :refer [defaults]]
            ["mongodb" :as mongodb]))

;;.connect the client is not needed > 4.7 (i use > 4.7 driver) client connects when crub
;;  but bug for some reason if not connect, so i connect before crub
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

;;mongodb://[user:pass@]host1:port1,host2:port2.../?option1&option2.....
;;default port= 27017 (if i dont add it )
;;Example "mongodb://user1:pwd1@host1/?authSource=db1&authMechanism=SCRAM-SHA-256&ssl=true"
;;https://docs.mongodb.com/manual/reference/connection-string/
;;srv(record) is used to connect to a host and from that host to get the cluster hosts
;;to avoid including all cluster hostes in the connection string,
;;its more flexible to changes also in cluster
;;mongodb+srv://[username:pass]@host/database?option1&option2...
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