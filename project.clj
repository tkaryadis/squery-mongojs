(defproject org.cmql/cmql-js "0.1.0-SNAPSHOT"
  :description "Query MongoDB with up to 3x less code (cljs/node-driver)"
  :url "https://github.com/tkaryadis/cmql-js"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/core.async "1.1.587"]
                 [org.clojure/clojurescript  "1.10.866"]
                 [org.cmql/cmql-core "0.1.0-SNAPSHOT"]
                 [frankiesardo/linked "1.3.0"]
                 [cljs-bean "1.6.0"]]

  :cljsbuild {:builds [{:id           "cmql-js"
                        :source-paths ["src"]
                        :compiler     {
                                       :output-to     "out/cmql.js"
                                       :main          "cmql-js.cmql-repl"
                                       :target        :nodejs
                                       :optimizations :none ;;advanced
                                       :install-deps   true
                                       :npm-deps  {:mongodb "4.0.0"}
                                       }}]}

  :plugins [[lein-cljsbuild "1.1.8"]
            [lein-npm "0.6.2" :hooks false ]
            [lein-codox "0.10.7"]]
            
  :codox {:language :clojurescript}
  )
