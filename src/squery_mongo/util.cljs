(ns squery-mongo.util
  (:require-macros [squery-mongo.util])
  (:require [cljs.core.async :refer [go  <! take!]]))

(defn js-async
  ([f cb] (take! (f) (fn [r] (cb r))))
  ([f] (js/Promise. (fn [resolve _] (go (resolve (<! (f))))))))

(defn js-async-fn [f]
  (fn [] (js-async f)))