(ns squery-mongo.driver.cursor
  (:require-macros [squery-mongo.driver.cursor])
  (:require cljs.pprint
            [cljs.core.async :refer [go go-loop <!]]
            [cljs.core.async.interop :refer-macros [<p!]]
    ;[squery-mongo.driver.document :refer [json->clj]]
    ;[cljs.nodejs :as nodejs]
            ))

(defn c-take-all [cursor]
  (go-loop [docs []]
    (if (<p! (.hasNext cursor))
      (recur (conj docs (<p! (.next cursor))))
      docs)))

(def mongodb (js/require "mongodb"))
(def AggregationCursor (.-AggregationCursor mongodb))
(def FindCursor (.-FindCursor mongodb))

(defn c-print-all [cursor]
  (go (cond
        (or (instance? AggregationCursor cursor)
            (instance? FindCursor cursor))
        (<! (go-loop []
              (if (<p! (.hasNext cursor))
                (let [doc (<p! (.next cursor))]
                  (do (if (map? doc)
                        (cljs.pprint/pprint doc)
                        (let [doc-str (.stringify js/JSON doc nil 2)]
                          (.log js/console doc-str)))
                      (recur)))))))))

(defn c-first-doc [docs-iterable]
  (let [iterator (.iterator docs-iterable)]
    (if (.hasNext iterator)
      (.next iterator)
      nil)))