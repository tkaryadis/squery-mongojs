(ns cmql-js.cmql-arguments
    (:require cmql-core.operators.operators))

(defmacro p [& args]
  `(let ~cmql-core.operators.operators/operators-mappings
     (apply cmql-js.internal.convert.cmql-arguments/jsp-f ~(vec args))))

(defmacro f
  "Takes many filters with aggregate operators,adds the $and,and the $expr
   Uses the same function that pipeline filters use but the match is removed"
  [& filters]
  `(get (first (apply cmql-js.internal.convert.cmql-arguments/jsp-f
                       (let ~cmql-core.operators.operators/operators-mappings
                         ~(into [] filters))))
         "$match"))

(defmacro u
  "Converts a cMQL update(not pipeline update) to a Java MQL update"
  [& update-operators]
  `(apply cmql-js.internal.convert.cmql-arguments/u-f
     (let ~cmql-core.operators.operators/operators-mappings
                ~(into [] update-operators))))

(defmacro qf
  "Takes many filters with aggregate operators,adds the $and,and the $expr
   Uses the same function that pipeline filters use but the match is removed"
  [& filters]
  `(clj->js (get (let ~cmql-core.operators.operators/operators-mappings
                   ~(into [] filters))
                 "$__qfilter__")))