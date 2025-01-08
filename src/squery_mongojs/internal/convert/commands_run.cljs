(ns squery-mongojs.internal.convert.commands-run
  (:require [squery-mongojs.internal.convert.common :refer [decode-js? rxjs?]]
            [squery-mongo-core.utils :refer [ordered-map]]
            [squery-mongojs.driver.document :refer [clj->shallow-js]]
    ;["rxjs" :as r :refer [from]]
            clojure.pprint))

(def  bson (js/require "bson"))

(def r (js/require "rxjs"))
(def from (.-from r))

(def serialize (.-serialize bson))
(def deserialize (.-deserialize bson))
(def deserializeCljs (.-deserializeCljs bson))

;;times
;;300 js return
;;600 raw+deserializeCljs
;;1200 js return + js->clj
(defn run-command [command-info command]
  (let [command-head (get command :command-head)
        command-body (get command :command-body)
        squery-map (merge (ordered-map command-head) command-body)

        db (get command-info :db)
        ;;serialize/desialize modified js-bson works directly with clojure maps/vectors
        ;;but for some reason the node driver,didnt work,if command's external was clojure map
        ;;so command is converted into a js-object (no values parsed,just the external map,becomes object)
        squery-map (clj->shallow-js squery-map)
        [squery-map decode-js?]  (decode-js? squery-map)
        [squery-map rxjs?] (rxjs? squery-map)
        ]
    (if decode-js?
      (if rxjs?
        (from (.command db squery-map))
        (.command db squery-map))
      (if rxjs?
        (from (-> (.command db squery-map #js {:raw true})
                  (.then (fn [result]
                           (deserializeCljs result)))))
        (-> (.command db squery-map #js {:raw true})
            (.then (fn [result]
                     (deserializeCljs result))))))))


;;------------------------------------Driver Methods--------------------------------------------------------------------

;;returns AggregationCursor can be converted to observable with (from)
(defn run-aggregation [command-info command]
  (let [command-body (get command :command-body)
        pipeline (get command-body "pipeline")
        pipeline (into-array pipeline)
        options (dissoc command-body "pipeline")
        options (clj->js options)
        [options js?] (decode-js? options)
        [options rxjs?] (rxjs? options)
        options (if js?
                  options
                  (do (aset options "promoteLongs" false)   ;;this is like trick in my BSON implementation to mean CLjs
                      options))
        coll (get command-info :coll)
        aggregate-cursor (.aggregate coll pipeline options)]
    (if rxjs?
      (from aggregate-cursor)
      aggregate-cursor)))

(defn run-find [command-info command]
  (let [command-body (get command :command-body)
        filter (get command-body "filter")
        options (dissoc command-body "filter")
        options (clj->js options)
        [options js?] (decode-js? options)
        [options rxjs?] (rxjs? options)
        options (if js?
                  options
                  (do (aset options "promoteLongs" false)
                      options))
        coll (get command-info :coll)
        find-cursor (.find coll filter options)]
    (if rxjs?
      (from find-cursor)
      find-cursor)))
