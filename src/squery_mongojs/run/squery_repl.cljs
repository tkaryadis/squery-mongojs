(ns squery-mongojs.run.squery-repl
  (:use squery-mongo-core.operators.operators
        squery-mongo-core.operators.qoperators
        squery-mongo-core.operators.uoperators
        squery-mongo-core.operators.stages
        squery-mongo-core.operators.options)
  #_(:require squery-mongo-core.operators.operators
            squery-mongo-core.operators.qoperators
            squery-mongo-core.operators.uoperators
            squery-mongo-core.operators.options
            squery-mongo-core.operators.stages
            [cljs.core.async :refer [go go-loop <!]]
            [cljs.core.async.interop :refer-macros [<p!]]
            [squery-mongojs.squery-arguments :refer-macros [p f qf u] :refer [o d]]
            [squery-mongojs.util :refer [golet squery]]
            [squery-mongojs.driver.cursor :refer [c-take-all c-print-all]]
            [squery-mongojs.driver.cursor :refer-macros [c-take-all! c-print-all!]]
            [squery-mongojs.driver.settings :refer [update-defaults defaults]]
            [squery-mongojs.driver.client :refer [create-mongo-client]]
            [squery-mongojs.commands :refer-macros [q fq insert]]
            [cljs-bean.core :refer [bean ->clj ->js]]))

(defn main [])

#_(update-defaults :connection-string "mongodb://localhost:27017")
#_(update-defaults :client (create-mongo-client) :decode "cljs")