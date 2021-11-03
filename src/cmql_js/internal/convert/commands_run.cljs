(ns cmql-js.internal.convert.commands-run
  (:require [cljs.core.async :refer [go <!]]
            [cljs.core.async.interop :refer-macros [<p!]]
            [cmql-js.internal.convert.common :refer [decode-js?]]
            [cmql-core.utils :refer [ordered-map]]
            [cmql-js.driver.document :refer [clj->shallow-js]]
            clojure.pprint))

(def  bson (js/require "bson"))

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
        cmql-map (merge (ordered-map command-head) command-body)

        db (get command-info :db)
        ;;serialize/desialize modified js-bson works directly with clojure maps/vectors
        ;;but for some reason the node driver,didnt work,if command's external was clojure map
        ;;so command is converted into a js-object (no values parsed,just the external map,becomes object)
        cmql-map (clj->shallow-js cmql-map)]
    (go (let [[cmql-map decode-js?]  (decode-js? cmql-map)
              result (if decode-js?
                       (<p! (.command db cmql-map))
                       (deserializeCljs (<p! (.command db cmql-map #js {:raw true}))))]
          result)
        #_(try (let [[cmql-map decode-js?]  (decode-js? cmql-map)
                   result (if decode-js?
                            (<p! (.command db cmql-map))
                            (deserializeCljs (<p! (.command db cmql-map #js {:raw true}))))]
               result)
             (catch js/Error err (do (js/console.log (ex-cause err))
                                     (clojure.pprint/pprint {:err (aget (ex-cause err) "codeName")
                                                             :command cmql-map ;(js->clj mql-map :keywordize-keys true)
                                                             })
                                     {:err (aget (ex-cause err) "codeName")
                                      :command cmql-map             ;(js->clj mql-map :keywordize-keys true)
                                      }))))))


;;------------------------------------Driver Methods--------------------------------------------------------------------


(defn run-aggregation [command-info command]
  (let [command-body (get command :command-body)
        ;_ (prn command-info)

        pipeline (get command-body "pipeline")
        pipeline (into-array pipeline)
        options (dissoc command-body "pipeline")
        options (clj->js options)
        [options js?] (decode-js? options)
        options (if js?
                  options
                  (do (aset options "promoteLongs" false)
                      options))
        coll (get command-info :coll)
        aggregate-cursor (.aggregate coll pipeline options)
        ]
    aggregate-cursor))

(defn run-find [command-info command]
  (let [command-body (get command :command-body)
        filter (get command-body "filter")
        options (dissoc command-body "filter")
        options (clj->js options)
        [options js?] (decode-js? options)
        options (if js?
                  options
                  (do (aset options "promoteLongs" false)
                      options))
        coll (get command-info :coll)
        find-cursor (.find coll filter options)
        ]
    find-cursor))
