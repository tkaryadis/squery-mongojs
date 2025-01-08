(ns squery-mongojs.interop.js)

(defn js->kclj [obj]
  (js->clj obj :keywordize-keys true))