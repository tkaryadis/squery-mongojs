(ns cmql-js.util
  (:require-macros [cmql-js.util])
  (:require [cljs.core.async :refer [go  <! take!]]))

(defn run-query
  ([f cb] (take! (f) (fn [r] (cb r))))
  ([f] (js/Promise. (fn [resolve _] (go (resolve (<! (f))))))))