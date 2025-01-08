(ns squery-mongojs.driver.document
  ;(:require [squery-mongo-core.utils :refer [ordered-map]])
  )

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

(defn print-doc [doc]
  (if (map? doc)
    (cljs.pprint/pprint doc)
    (let [doc-str (js/JSON.stringify doc nil 2)]
      (.log js/console doc-str))))

#_(defn clj-doc
  ([omap]
   (clj->js omap))
  ([k1 v1 & kvs]
   (let [omap (apply ordered-map (concat [k1 v1] kvs))]
     (clj->js omap))))