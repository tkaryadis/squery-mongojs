(ns squery-mongojs.internal.convert.squery-arguments
  (:require [squery-mongo-core.internal.convert.common :refer [single-maps]]
            [squery-mongo-core.internal.convert.commands
             :refer [get-pipeline-options squery-pipeline->mql-pipeline args->query-updateOperators-options squery-map->mql-map]]))

(defn jsp-f [& args]
  (let [args (single-maps args)
        [pipeline args] (get-pipeline-options args #{})
        pipeline (squery-pipeline->mql-pipeline pipeline)
        pipeline-map {:pipeline pipeline}
        pipeline-map (squery-map->mql-map pipeline-map)
        pipeline (get pipeline-map "pipeline")
        pipeline (into-array pipeline)
        ]
    pipeline))


(defn u-f [& args]
  (let [[query update-operators options] (args->query-updateOperators-options args #{})
        update-operators (apply (partial merge {}) update-operators)]
    (clj->js update-operators)))