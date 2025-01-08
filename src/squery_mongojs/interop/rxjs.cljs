(ns squery-mongojs.interop.rxjs)

(def o (js/require "rxjs/operators"))
(def r (js/require "rxjs"))

(def from (.-from r))
(def of (.-of r))

(defn rxmap [ob f] (.pipe ob (.map o f)))
(defn map-to [ob value] (.pipe ob (.mapTo o value)))
(defn rxfilter [ob f] (.pipe ob (.filter o f)))
(defn rxreduce [ob f init] (.pipe ob (.reduce o f init)))
(defn concatMap [ob f] (.pipe ob (.concatMap o f)))
(defn tap [ob f] (.pipe ob (.tap o f)))
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