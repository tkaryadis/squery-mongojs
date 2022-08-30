(ns squery-mongo.squery-arguments
    (:require squery-mongo-core.operators.operators))

(defmacro p [& args]
  `(let ~squery-mongo-core.operators.operators/operators-mappings
     (apply squery-mongo.internal.convert.squery-arguments/jsp-f ~(vec args))))

(defmacro f
  "Takes many filters with aggregate operators,adds the $and,and the $expr
   Uses the same function that pipeline filters use but the match is removed"
  [& filters]
  `(get (first (apply squery-mongo.internal.convert.squery-arguments/jsp-f
                       (let ~squery-mongo-core.operators.operators/operators-mappings
                         ~(into [] filters))))
         "$match"))

(defmacro u
  "Converts a squery update(not pipeline update) to a Java MQL update"
  [& update-operators]
  `(apply squery-mongo.internal.convert.squery-arguments/u-f
     (let ~squery-mongo-core.operators.operators/operators-mappings
                ~(into [] update-operators))))

(defmacro qf
  "Takes many filters with aggregate operators,adds the $and,and the $expr
   Uses the same function that pipeline filters use but the match is removed"
  [& filters]
  `(clj->js (get (let ~squery-mongo-core.operators.operators/operators-mappings
                   ~(into [] filters))
                 "$__qfilter__")))