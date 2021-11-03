(ns cmql-js.driver.settings)

;;defaults,i can add anything i want,but cMQL-j will look those if i dont give a argument it needs
#_{:client client                              ;;default
   :session session                            ;;default
   :db db                                      ;;default
   :coll coll                                  ;;no default user must always give this if needed

   ;;?TODO
   :registry registry                          ;;no default value,default to Document if decode=js/cljs,defaults to pojo if decode a Class!=Document
   :decode decode                              ;;default (accepted defaults only java or clj)
   }

(def defaults-map (atom {:connection-string "mongodb://localhost:27017"
                         :session nil}))

(defn defaults [k]
  (get @defaults-map k))

(defn update-defaults [& kvs]
  (loop [kvs (partition 2 kvs)]
    (if-not (empty? kvs)
      (let [kv (first kvs)]
        (recur (do (swap! defaults-map assoc (first kv) (second kv)) (rest kvs)))))))

;;default client
#_(def mongo-client (atom nil))

#_(defn set-mongo-client [new-mongo-client]
  (reset! mongo-client new-mongo-client))

#_(defn get-mongo-client []
  @mongo-client)

;;default session
#_(def mongo-session (atom nil))

#_(defn set-mongo-session [new-mongo-client]
  (reset! mongo-session new-mongo-client))

#_(defn get-mongo-session []
  @mongo-session)

;;default decode
#_(def default-decode-java (atom true))

#_(defn set-default-decode-java [java-decode?]
  (reset! default-decode-java java-decode?))

#_(defn get-decode-java []
  @default-decode-java)