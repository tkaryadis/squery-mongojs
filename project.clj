(defproject org.squery/squery-mongo-js "0.2.0-SNAPSHOT"
  :description "Query MongoDB with up to 3x less code (cljs/node-driver)"
  :url "https://github.com/tkaryadis/cmql-js"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/core.async "1.1.587"]
                 [org.clojure/clojurescript  "1.10.866"]
                 [org.squery/squery-mongo-core "0.2.0-SNAPSHOT"]
                 [frankiesardo/linked "1.3.0"]
                 [cljs-bean "1.6.0"]]

  :cljsbuild {:builds [{:id           "squery-js"
                        :source-paths ["src"]
                        :compiler     {
                                       :output-to     "out/squery.js"
                                       :main          "squery-mongo.test" ;;  "squery-mongo.squery-repl"
                                       :target        :nodejs
                                       :optimizations :none ;;advanced
                                       :install-deps   true
                                       :npm-deps  {:mongodb "4.11.0"}
                                       }}]}

  :plugins [[lein-cljsbuild "1.1.8"]
            [lein-npm "0.6.2" :hooks false ]
            [lein-codox "0.10.7"]]
            
  :codox {:language :clojurescript}
  )
