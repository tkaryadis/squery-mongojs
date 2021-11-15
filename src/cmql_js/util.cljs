(ns cmql-js.util
  (:require-macros [cmql-js.util])
  (:require [cljs.core.async :refer [go  <! take!]]))

(defn js-cb [f cb]
  (take! (f) (fn [r] (cb r))))

(defn js-p [f]
  (js/Promise. (fn [resolve _] (go (resolve (<! (f)))))))