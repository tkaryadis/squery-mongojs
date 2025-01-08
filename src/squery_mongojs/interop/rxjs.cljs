(ns squery-mongojs.interop.rxjs
  #_(:require ["rxjs/operators" :as o ]
            ["rxjs" :as r :refer [from of range timer Notification concat fromEvent pipe zip]]))

(def o (js/require "rxjs/operators"))
(def r (js/require "rxjs"))

(def from (.-from r))
(def of (.-of r))

#_(defn rxmap [ob f] (.pipe ob (o/map f)))
#_(defn map-to [ob value] (.pipe ob (o/mapTo value)))
#_(defn rxfilter [ob f] (.pipe ob (o/filter f)))
#_(defn rxreduce [ob f init] (.pipe ob (o/reduce f init)))
#_(defn concatMap [ob f] (.pipe ob (o/concatMap f)))
#_(defn tap [ob f] (.pipe ob (o/tap f)))
(defn then
  ;;returning nil can cause problems as nil is like missing argument to the next fn
  ([ob]
   (.pipe ob (.concatMap o (fn [_] (of (js/undefined))))))
  ([ob then-ob]
   (.pipe ob (.concatMap o (fn [_] then-ob)))))

;;error
#_(defn catchError [ob f] (.pipe ob (o/catchError f)))
#_(defn onErrorResumeNextWith [ob error-ob] (.pipe ob (r/onErrorResumeNextWith error-ob)))

(defn then
  ;;returning nil can cause problems as nil is like missing argument to the next fn
  ([ob]
   (.pipe ob (.concatMap o (fn [_] (of (js/undefined))))))
  ([ob then-ob]
   (.pipe ob (.concatMap o (fn [_] then-ob)))))

(defn subscribe
  ([ob] (.subscribe ob))
  ([ob next] (.subscribe ob #js {:next next}))
  ([ob next err] (.subscribe ob #js {:next next :error err}))
  ([ob next err complete] (.subscribe ob #js {:next next :error err :complete complete})))