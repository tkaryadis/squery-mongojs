(ns cmql-js.driver.document
  (:require [cmql-core.utils :refer [ordered-map]]))

(defn clj->shallow-js
  "Changes the external clj-map to js-map (doesn't change the keys or the values)"
  [m]
  (let [jobj (js/Object.)]
    (loop [ks (keys m)]
      (if (empty? ks)
        jobj
        (let [k (first ks)
              _ (aset jobj k (get m k))]
          (recur (rest ks)))))))

(defn js->clj-k [js-obj]
  (js->clj js-obj
           :keywordize-keys true))

#_(def default-decode-js (atom false))

#_(defn set-defaultDecode-js [decodejs]
  (reset! default-decode-js decodejs))

#_(defn get-default-decode-js []
  @default-decode-js)

#_(defn clj-doc
  ([omap]
   (clj->js omap))
  ([k1 v1 & kvs]
   (let [omap (apply ordered-map (concat [k1 v1] kvs))]
     (clj->js omap))))




#_(defn clj->json [m]
  (generate-string m))

#_(defn json->clj [m]
  (parse-string m))