(ns cmql-js.internal.convert.cmql-arguments
  (:require [cmql-core.internal.convert.common :refer [single-maps]]
            [cmql-core.internal.convert.commands
             :refer [get-pipeline-options cmql-pipeline->mql-pipeline cmql-map->mql-map]]))

(defn jsp-f [& args]
  (let [args (single-maps args)
        [pipeline args] (get-pipeline-options args #{})
        pipeline (cmql-pipeline->mql-pipeline pipeline)
        pipeline-map {:pipeline pipeline}
        pipeline-map (cmql-map->mql-map pipeline-map)
        pipeline (get pipeline-map "pipeline")
        pipeline (into-array pipeline)
        ]
    pipeline))