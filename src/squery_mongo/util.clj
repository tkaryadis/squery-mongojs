(ns squery-mongo.util
  (:require [cljs.core.async :refer [go]]
             squery-mongo-core.operators.operators))

(defmacro golet
  "Like (go (let ...))"
  [bindings & body]
  `(go (let ~bindings ~@body)))

(defmacro squery
  "squery code should be under this enviroment,this enviroment is auto-included from query macros.
   But its not included in the operators
   (defn myf []
     (squery (+ 1 2)))   ;;to generate {'$add' [1 2]}
   "
  [squery-code]
  `(let ~squery-mongo-core.operators.operators/operators-mappings
     ~squery-code))
