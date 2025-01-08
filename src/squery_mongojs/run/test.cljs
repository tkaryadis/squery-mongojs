(ns squery-mongojs.run.test
  (:use squery-mongo-core.operators.operators
        squery-mongo-core.operators.qoperators
        squery-mongo-core.operators.uoperators
        squery-mongo-core.operators.stages)
  (:require squery-mongo-core.operators.operators
            squery-mongo-core.operators.qoperators
            squery-mongo-core.operators.uoperators
            squery-mongo-core.operators.options
            squery-mongo-core.operators.stages
            [squery-mongojs.squery-arguments :refer-macros [p f qf u] :refer [o d]]
    ;[squery-mongojs.driver.cursor :refer [c-take-all c-print-all]]
            [squery-mongojs.driver.cursor :refer [take-all]]
            [squery-mongojs.driver.settings :refer [update-defaults defaults]]
            [squery-mongojs.driver.client :refer [create-mongo-client]]
            [squery-mongojs.commands :refer-macros [q fq insert dq uq drop-collection]]
            [cljs-bean.core :refer [bean ->clj ->js]]
            [cljs.reader :refer [read-string]]
            [squery-mongojs.driver.document :refer [print-doc]]
            cljs.pprint
            #_["rxjs" :as r :refer [from of range timer Notification concat fromEvent pipe zip bindNodeCallback]]
            #_["rxjs/operators" :as o :refer []]
            #_["rxjs/internal/observable/innerFrom" :as rfrom :refer [fromAsyncIterable fromIterable fromPromise]]
            [squery-mongojs.interop.rxjs :refer [of from then subscribe]]
    ;[shadow.cljs.modern :refer [js-await]]
            ))

(defn main [])

(update-defaults :connection-string "mongodb://localhost:27017")
(update-defaults :client (create-mongo-client) :decode "cljs" :rxjs true)

;;needs rxjs true
(-> (from (.connect (defaults :client)))
    (then (drop-collection :testdb.testcoll))
    (then (insert :testdb.testcoll [{:a [{:b [10 20]}]} {:c 10}]))
    (then (q :testdb.testcoll))
    (subscribe (fn [doc] (print-doc doc))))

;;works for rxjs false/true because from if true doesnt do anything
#_(-> (from (.connect (defaults :client)))
      (then (from (drop-collection :testdb.testcoll)))
      (then (from (insert :testdb.testcoll [{:a [{:b [10 20]}]} {:c 10}])))
      (then (from (q :testdb.testcoll)))
      (subscribe (fn [d] (prn d))))

;;works only with rxjs false
#_(-> (.connect (defaults :client))
      (.then (fn [_]
             (insert :testdb.testcoll [{:a [{:b [10 20]}]} {:c 10}]))))

;;works only with rxjs false
#_(js-await [_ (.connect (defaults :client))]
          (js-await [_ (insert :testdb.testcoll [{:a [{:b [10 20]}]} {:c 10}])]
                    (prn "done!")))

;;doesnt work
#_(js-await [_ (.connect (defaults :client))
           _ (insert :testdb.testcoll [{:a [{:b [10 20]}]} {:c 10}])]
          (prn "done"))

;;p/let of promesa for linear way not nested


