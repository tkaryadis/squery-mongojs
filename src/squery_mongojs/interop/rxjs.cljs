(ns squery-mongojs.interop.rxjs
  (:require ["rxjs/operators" :as o ]
            ["rxjs" :as r :refer [from of range timer Notification concat fromEvent pipe zip]]))

(defn rxmap [ob f] (.pipe ob (o/map f)))
(defn map-to [ob value] (.pipe ob (o/mapTo value)))
(defn rxfilter [ob f] (.pipe ob (o/filter f)))
(defn concatMap [ob f] (.pipe ob (o/concatMap f)))
(defn tap [ob f] (.pipe ob (o/tap f)))
(defn then
  ;;returning nil can cause problems as nil is like missing argument to the next fn
  ([ob]
   (.pipe ob (o/concatMap (fn [_] (of (js/undefined))))))
  ([ob then-ob]
   (.pipe ob (o/concatMap (fn [_] then-ob)))))

;;error
(defn catchError [ob f] (.pipe ob (o/catchError f)))
(defn onErrorResumeNextWith [ob error-ob] (.pipe ob (r/onErrorResumeNextWith error-ob)))

(defn subscribe
  ([ob] (.subscribe ob))
  ([ob next] (.subscribe ob #js {:next next}))
  ([ob next err] (.subscribe ob #js {:next next :error err}))
  ([ob next err complete] (.subscribe ob #js {:next next :error err :complete complete})))