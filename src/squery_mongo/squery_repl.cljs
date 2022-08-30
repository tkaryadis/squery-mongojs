(ns squery-mongo.squery-repl
  (:use squery-mongo-core.operators.operators
        squery-mongo-core.operators.qoperators
        squery-mongo-core.operators.uoperators
        squery-mongo-core.operators.stages
        squery-mongo-core.operators.options)
  (:require squery-mongo-core.operators.operators
            squery-mongo-core.operators.qoperators
            squery-mongo-core.operators.uoperators
            squery-mongo-core.operators.options
            squery-mongo-core.operators.stages
            [cljs.core.async :refer [go go-loop <!]]
            [cljs.core.async.interop :refer-macros [<p!]]
            [squery-mongo.squery-arguments :refer-macros [p f qf u] :refer [o d]]
            [squery-mongo.util :refer [golet squery]]
            [squery-mongo.driver.cursor :refer [c-take-all c-print-all]]
            [squery-mongo.driver.cursor :refer-macros [c-take-all! c-print-all!]]
            [squery-mongo.driver.settings :refer [update-defaults defaults]]
            [squery-mongo.driver.client :refer [create-mongo-client]]
            [squery-mongo.commands :refer-macros [q fq insert insert! find-and-modify!]]
            [cljs-bean.core :refer [bean ->clj ->js]]))

(update-defaults :connection-string "mongodb://localhost:27017")
(update-defaults :client (create-mongo-client) :decode "cljs")


(def mongodb (js/require "mongodb"))

(def MongoClient (.-MongoClient mongodb))

;;test pipeline update not supported in nodejs driver
(go (try
      (let [client (<p! (.connect (defaults :client)))
            coll (.collection (.db client "testdb") "testcoll")
            _ (try (<p! (.drop coll)) (catch :default e e))

            _ (insert! :testdb.testcoll {:a 1})
            _ (c-print-all! (q :testdb.testcoll))
            ;_
            #_(<p! (.updateOne coll
                               #js {}
                               #_(p {:a 2})
                               #js {"$set" {:a 2}}
                               ;;#js [{"$set" {:a 2}}]
                               ))

            _ (<p! (.updateOne coll
                               (d {:a 4})
                               (u (+_ :a 2))
                               (o {:upsert true})))
            _ (c-print-all! (q :testdb.testcoll))
            ]
        "done")
      (catch :default e (prn (.toString e)))))