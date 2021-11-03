(ns cmql-js.util
  (:require [cljs.core.async :refer [go]]
             cmql-core.operators.operators))

(defmacro golet
  "Like (go (let ...))"
  [bindings & body]
  `(go (let ~bindings ~@body)))

(defmacro cmql
  "cMQL code should be under this enviroment,this enviroment is auto-included from query macros.
   But its not included in the operators
   (defn myf []
     (cqml (+ 1 2)))   ;;to generate {'$add' [1 2]}
   "
  [cmql-code]
  `(let ~cmql-core.operators.operators/operators-mappings
     ~cmql-code))
