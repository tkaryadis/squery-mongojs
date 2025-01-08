(ns squery-mongojs.driver.cursor
  (:require cljs.pprint))

(def mongodb (js/require "mongodb"))
(def AggregationCursor (.-AggregationCursor mongodb))
(def FindCursor (.-FindCursor mongodb))

#_(defn c-take-all [cursor]
  (go-loop [docs []]
           (if (<p! (.hasNext cursor))
             (recur (conj docs (<p! (.next cursor))))
             docs)))

#_(defn c-print-all [cursor]
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

#_(defn c-first-doc [docs-iterable]
  (let [iterator (.iterator docs-iterable)]
    (if (.hasNext iterator)
      (.next iterator)
      nil)))