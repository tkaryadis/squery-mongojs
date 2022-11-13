(ns squery-mongo.test
  (:use squery-mongo-core.operators.operators
        squery-mongo-core.operators.qoperators
        squery-mongo-core.operators.uoperators
        squery-mongo-core.operators.stages)
  (:require squery-mongo-core.operators.operators
            squery-mongo-core.operators.qoperators
            squery-mongo-core.operators.uoperators
            squery-mongo-core.operators.options
            squery-mongo-core.operators.stages
            [cljs.core.async :refer [go go-loop <! chan close! take!]]
            [cljs.core.async.interop :refer-macros [<p!]]
            [squery-mongo.squery-arguments :refer-macros [p f qf u] :refer [o d]]
            [squery-mongo.driver.cursor :refer [c-take-all c-print-all]]
            [squery-mongo.driver.cursor :refer-macros [c-take-all! c-print-all!]]
            [squery-mongo.driver.settings :refer [update-defaults defaults]]
            [squery-mongo.driver.client :refer [create-mongo-client]]
            [squery-mongo.commands :refer-macros [q fq insert insert! delete! dq update-! uq drop-collection!]]
            [squery-mongo.util :refer [js-async] :refer-macros [golet gotry squery]]
            [cljs-bean.core :refer [bean ->clj ->js]]
            [cljs.reader :refer [read-string]]
            cljs.pprint))

(defn main [])

(update-defaults :connection-string "mongodb://localhost:27017")
(update-defaults :client (create-mongo-client) :decode "cljs")

;;test pipeline update not supported in nodejs driver
(gotry (<p! (.connect (defaults :client)))
       (drop-collection! :testdb.testcoll)
       (insert! :testdb.testcoll {:a [{:b [10 20]}]})
       (prn "before update")
       (c-print-all! (q :testdb.testcoll))
       (update-! :testdb.testcoll (uq (set!- :a 10)))
       (prn "after update")
       (c-print-all! (q :testdb.testcoll))
       (.exit js/process)
       (catch :default e (prn (.toString e))))