(ns squery-mongo.internal.convert.common
  (:require [squery-mongo.driver.settings :refer [defaults]]))

(defn decode-js? [options]
  (if (.hasOwnProperty options "decode")
    (let [js? (= (.-decode options) "js")]
      [(do (js-delete options "decode") options) js?])
    [options (= (defaults :decode) "js")]))