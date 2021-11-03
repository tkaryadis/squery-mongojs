(ns cmql-js.driver.cursor)

(defmacro c-take-all! [cursor]
  `(cljs.core.async/<! (cmql-js.driver.cursor/c-take-all ~cursor)))

(defmacro c-print-all! [cursor]
  `(cljs.core.async/<! (cmql-js.driver.cursor/c-print-all ~cursor)))