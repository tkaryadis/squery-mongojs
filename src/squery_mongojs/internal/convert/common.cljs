(ns squery-mongojs.internal.convert.common
  (:require [squery-mongojs.driver.settings :refer [defaults]]))

;;each query can run with its own options, if no option, then the default is used
(defn decode-js? [options]
  (if (.hasOwnProperty options "decode")
    (let [js? (= (.-decode options) "js")]
      [(do (js-delete options "decode") options) js?])
    [options (= (defaults :decode) "js")]))

(defn rxjs? [options]
  (if (.hasOwnProperty options "rxjs")
    (let [rxjs? (= (.-rxjs options) true)]
      [(do (js-delete options "rxjs") options) rxjs?])
    [options (= (defaults :rxjs) true)]))