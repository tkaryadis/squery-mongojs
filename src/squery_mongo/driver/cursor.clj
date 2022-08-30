(ns squery-mongo.driver.cursor)

(defmacro c-take-all! [cursor]
  `(cljs.core.async/<! (squery-mongo.driver.cursor/c-take-all ~cursor)))

(defmacro c-print-all! [cursor]
  `(cljs.core.async/<! (squery-mongo.driver.cursor/c-print-all ~cursor)))