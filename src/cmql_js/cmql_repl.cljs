(ns cmql-js.cmql-repl
  (:use cmql-core.operators.operators
        cmql-core.operators.stages)
  (:require cmql-core.operators.operators
            cmql-core.operators.options
            cmql-core.operators.stages
            [cljs.core.async :refer [go go-loop <!]]
            [cljs.core.async.interop :refer-macros [<p!]]
            [cmql-js.cmql-arguments :refer-macros [p f qf] :refer [o d]]
            [cmql-js.util :refer [golet cmql]]
            [cmql-js.driver.cursor :refer [c-take-all c-print-all]]
            [cmql-js.driver.cursor :refer-macros [c-take-all!]]
            [cmql-js.driver.settings :refer [update-defaults defaults]]
            [cmql-js.driver.client :refer [create-mongo-client]]
            [cmql-js.commands :refer-macros [q fq insert insert! find-and-modify!]]
            [cljs-bean.core :refer [bean ->clj ->js]]))

(update-defaults :connection-string "mongodb://localhost:27017")
(update-defaults :client (create-mongo-client) :decode "cljs")


#_(golet [client (<p! (.connect (defaults :client)))
        admin-db (.admin (.db client))]
  (prn (<p! (.listDatabases admin-db (o)))))