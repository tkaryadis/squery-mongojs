(ns squery-mongo.internal.convert.commands
  (:require [squery-mongo-core.internal.convert.commands :refer [split-db-namespace]]
            [squery-mongo.driver.settings :refer [defaults]]))

(def mongodb (js/require "mongodb"))
(def Collection (.-Collection mongodb))
(def Db (.-Db mongodb))

(defn get-db-namespace
  "Commands are allowed to be called with object arguments like MongoCollection
   But to get the command we need string format"
  [command-info]
  (cond

    (keyword? command-info)
    command-info

    (instance? Collection command-info)
    (let [coll command-info
          db-name (.-dbName coll)
          coll-name (.-collectionName coll)]
      (keyword (str db-name "." coll-name)))

    (instance? Db command-info)
    (let [db command-info]
      (keyword (str (.databaseName db))))

    :else     ;;coll-info
    nil
    #_(let [db-name (name (get command-info :db-name (.getName (defaults :db))))
          db-namespace (cond

                         (contains? command-info :coll)
                         (let [coll (get command-info :coll)
                               mongoNamespace (.getNamespace ^MongoCollection coll)
                               coll-name (.getCollectionName mongoNamespace)
                               db-name (.getDatabaseName mongoNamespace)]
                           (keyword (str db-name "." coll-name)))

                         (contains? command-info :coll-name)
                         (keyword (str db-name "." (name (get command-info :coll-name))))

                         :else
                         (keyword db-name))]
      db-namespace)))

(defn command-info-from-db-namespace [db-namespace]
  (let [client (defaults :client)
        [db-name coll-name] (split-db-namespace db-namespace)
        db  (.db client db-name) ;;i always need a database
        coll (if (some? coll-name) (.collection db coll-name) nil) ;;coll is optional depends on command
        ]
    {:db db
     :coll coll
     :db-name db-name
     :coll-name coll-name
     :complete? true}))

(defn command-info-from-coll [coll]
  (let [client (defaults :client)
        db-name (.-dbName coll)
        db (.db client db-name)
        coll-name (.-collectionName coll)]
    {:client client
     :db db
     :coll coll
     :db-name db-name
     :coll-name coll-name
     :complete? true}))

(defn command-info-from-database [db]
  (let [client (defaults :client)
        db-name (.databaseName db)]
    {:client client
     :db db
     :coll nil
     :db-name db-name
     :coll-name nil
     :complete? true}))

(defn command-info-from-command-info [command-info]
  (let [client (get command-info :client (defaults :client))
        [db db-name] (cond
                       (and (contains? command-info :db) (contains? command-info :db-name))
                       [(get command-info :db) (get command-info :db-name)]

                       (contains? command-info :db)
                       [(get command-info :db) (.databaseName (get command-info :db))]

                       (contains? command-info :db-name)
                       [(.db client (get command-info :db-name)) (get command-info :db-name)]

                       :else
                       [nil nil])

        [coll coll-name] (cond
                           (and (contains? command-info :coll) (contains? command-info :coll-name))
                           [(get command-info :coll) (get command-info :coll-name)]

                           (contains? command-info :coll)
                           [(get command-info :coll) (.-dbName (get command-info :coll))]

                           (contains? command-info :coll-name)
                           [(.collection db (get command-info :coll-name)) (get command-info :coll-name)]

                           :else
                           [nil nil])]
    {:client client
     :db db
     :coll coll
     :db-name db-name
     :coll-name coll-name
     :complete? true}))

(defn get-command-info
  "Arguments
   {
     :client client
     :db db
     :coll coll
     :db-name db-name
     :coll-name coll-name
   }
  "
  [command-info]
  (cond

    (keyword? command-info)
    (command-info-from-db-namespace command-info)

    (instance? Collection command-info)
    (command-info-from-coll command-info)

    (instance? Db command-info)
    (command-info-from-database command-info)

    :else     ;;coll-info-map
    (command-info-from-command-info command-info)))