(ns squery-mongojs.driver.cursor
  (:require
    #_["rxjs" :as r :refer [from of firstValueFrom range timer Notification concat fromEvent pipe zip bindNodeCallback]]
    ;["rxjs/operators" :as o :refer []]
            #_["rxjs/internal/observable/innerFrom" :as rfrom :refer [fromAsyncIterable fromIterable fromPromise]]
            #_[squery-mongojs.interop.rxjs :refer [subscribe rxmap map-to rxfilter concatMap tap catchError
                                                 then onErrorResumeNextWith]]
            cljs.pprint))

(def o (js/require "rxjs/operators"))

(def mongodb (js/require "mongodb"))
(def AggregationCursor (.-AggregationCursor mongodb))
(def FindCursor (.-FindCursor mongodb))

;;of(1,2,3)
;.pipe(reduce((acc,val) => acc+val,100))
;.subscribe(d=>console.log(d))

(defn take-all [ob]
  (-> ob
      (.pipe
        (.reduce o (fn [col doc] (conj col doc)) []))))

#_(defn print-doc [doc]
  (-> ob
      (.pipe
        (o/tap (fn [doc]
                 (if (map? doc)
                   (cljs.pprint/pprint doc)
                   (let [doc-str (js/JSON.stringify doc nil 2)]
                     (.log js/console doc-str))))))))

#_(defn print-all [ob]
  (-> ob
      (subscribe (fn [doc]
                   (if (map? doc)
                     (cljs.pprint/pprint doc)
                     (let [doc-str (js/JSON.stringify doc nil 2)]
                       (.log js/console doc-str)))))))

#_(defn c-first-doc [docs-iterable]
  (let [iterator (.iterator docs-iterable)]
    (if (.hasNext iterator)
      (.next iterator)
      nil)))