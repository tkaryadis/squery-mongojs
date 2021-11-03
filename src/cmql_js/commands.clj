(ns cmql-js.commands
  (:require cmql-core.operators.operators))

;;--------------------------------------commands------------------------------------------------------------------------

(defmacro insert [command-coll-info documents & args]
  `(cmql-js.internal.convert.commands-run/run-command
     (cmql-js.internal.convert.commands/get-command-info ~command-coll-info)
     (apply cmql-core.read-write/insert
            ~(vec (cons `(cmql-js.internal.convert.commands/get-db-namespace ~command-coll-info)
                        (cons documents args))))))

(defmacro insert! [command-coll-info documents & args]
  `(cljs.core.async/<!
     (cmql-js.internal.convert.commands-run/run-command
       (cmql-js.internal.convert.commands/get-command-info ~command-coll-info)
       (apply cmql-core.read-write/insert
              ~(vec (cons `(cmql-js.internal.convert.commands/get-db-namespace ~command-coll-info)
                          (cons documents args)))))))

(defmacro dq [& args]
  `(let ~cmql-core.operators.operators/operators-mappings
     (apply cmql-core.read-write/dq-f ~(vec args))))

(defmacro delete [command-coll-info & args]
  `(cmql-js.internal.convert.commands-run/run-command
     (cmql-js.internal.convert.commands/get-command-info ~command-coll-info)
     (apply cmql-core.read-write/delete ~(vec (cons `(cmql-js.internal.convert.commands/get-db-namespace ~command-coll-info) args)))))

(defmacro delete! [command-coll-info & args]
  `(cljs.core.async/<!
     (cmql-js.internal.convert.commands-run/run-command
       (cmql-js.internal.convert.commands/get-command-info ~command-coll-info)
       (apply cmql-core.read-write/delete ~(vec (cons `(cmql-js.internal.convert.commands/get-db-namespace ~command-coll-info) args))))))

(defmacro uq [& args]
  `(let ~cmql-core.operators.operators/operators-mappings
     (apply cmql-core.read-write/uq-f ~(vec args))))

(defmacro update- [command-coll-info & args]
  `(cmql-js.internal.convert.commands-run/run-command
     (cmql-js.internal.convert.commands/get-command-info ~command-coll-info)
     (apply cmql-core.read-write/update- ~(vec (cons `(cmql-js.internal.convert.commands/get-db-namespace ~command-coll-info) args)))))

(defmacro update-! [command-coll-info & args]
  `(cljs.core.async/<!
     (cmql-js.internal.convert.commands-run/run-command
       (cmql-js.internal.convert.commands/get-command-info ~command-coll-info)
       (apply cmql-core.read-write/update- ~(vec (cons `(cmql-js.internal.convert.commands/get-db-namespace ~command-coll-info) args))))))

(defmacro find-and-modify [command-coll-info & args]
  `(cmql-js.internal.convert.commands-run/run-command
     (cmql-js.internal.convert.commands/get-command-info ~command-coll-info)
     (let ~cmql-core.operators.operators/operators-mappings
       (apply cmql-core.read-write/find-and-modify-f
              ~(vec (cons `(cmql-js.internal.convert.commands/get-db-namespace ~command-coll-info) args))))))

(defmacro find-and-modify! [command-coll-info & args]
  `(cljs.core.async/<!
     (cmql-js.internal.convert.commands-run/run-command
       (cmql-js.internal.convert.commands/get-command-info ~command-coll-info)
       (let ~cmql-core.operators.operators/operators-mappings
         (apply cmql-core.read-write/find-and-modify-f
                ~(vec (cons `(cmql-js.internal.convert.commands/get-db-namespace ~command-coll-info) args)))))))

(defmacro q-count [command-coll-info & args]
  `(cmql-js.internal.convert.commands-run/run-command
     (cmql-js.internal.convert.commands/get-command-info ~command-coll-info)
     (let ~cmql-core.operators.operators/operators-mappings
       (apply cmql-core.read-write/q-count-f
              ~(vec (cons `(cmql-js.internal.convert.commands/get-db-namespace ~command-coll-info) args))))))

(defmacro q-count! [command-coll-info & args]
  `(cljs.core.async/<!
     (cmql-js.internal.convert.commands-run/run-command
       (cmql-js.internal.convert.commands/get-command-info ~command-coll-info)
       (let ~cmql-core.operators.operators/operators-mappings
         (apply cmql-core.read-write/q-count-f
                ~(vec (cons `(cmql-js.internal.convert.commands/get-db-namespace ~command-coll-info) args)))))))

(defmacro q-distinct [command-coll-info & args]
  `(cmql-js.internal.convert.commands-run/run-command
     (cmql-js.internal.convert.commands/get-command-info ~command-coll-info)
     (let ~cmql-core.operators.operators/operators-mappings
       (apply cmql-core.read-write/q-distinct-f
              ~(vec (cons `(cmql-js.internal.convert.commands/get-db-namespace ~command-coll-info) args))))))

(defmacro q-distinct! [command-coll-info & args]
  `(cljs.core.async/<!
     (cmql-js.internal.convert.commands-run/run-command
       (cmql-js.internal.convert.commands/get-command-info ~command-coll-info)
       (let ~cmql-core.operators.operators/operators-mappings
         (apply cmql-core.read-write/q-distinct-f
                ~(vec (cons `(cmql-js.internal.convert.commands/get-db-namespace ~command-coll-info) args)))))))


;;------------------------------------------methods called like commands------------------------------------------------

(defmacro q [command-coll-info & args]
  `(cmql-js.internal.convert.commands-run/run-aggregation
     (cmql-js.internal.convert.commands/get-command-info ~command-coll-info)
     (let ~cmql-core.operators.operators/operators-mappings
       (apply cmql-core.read-write/q-f
              ~(vec (cons `(cmql-js.internal.convert.commands/get-db-namespace ~command-coll-info) args))))))

(defmacro fq [command-coll-info & args]
  `(cmql-js.internal.convert.commands-run/run-find
     (cmql-js.internal.convert.commands/get-command-info ~command-coll-info)
     (let ~cmql-core.operators.operators/operators-mappings
       (apply cmql-core.read-write/fq-f ~(vec (cons `(cmql-js.internal.convert.commands/get-db-namespace ~command-coll-info) args))))))
