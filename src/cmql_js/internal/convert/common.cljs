(ns cmql-js.internal.convert.common
  (:require [cmql-js.driver.settings :refer [defaults]]))

(defn decode-js? [options]
  (if (.hasOwnProperty options "decode")
    (let [js? (= (.-decode options) "js")]
      [(do (js-delete options "decode") options) js?])
    [options (= (defaults :decode) "js")]))