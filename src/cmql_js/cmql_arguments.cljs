(ns cmql-js.cmql-arguments
  (:require-macros [cmql-js.cmql-arguments])
  (:require cmql-js.internal.convert.cmql-arguments
            [cmql-js.internal.convert.common :refer [decode-js?]]))

(defn o [& args]
  (let [options (apply (partial merge {}) (map js->clj args))
        options (clj->js options)
        [options js?] (decode-js? options)
        options (if js?
                  options
                  (do (aset options "promoteLongs" false)
                      options))]
    options))

(defn d
  "same as clj->js,preferred because special meaning of making a document"
  [clj-map]
  (clj->js clj-map))

(defn ds [& clj-maps]
  (mapv d clj-maps))