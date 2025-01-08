(ns squery-mongojs.interop.js)

(defmacro js-await [[name thenable] & body]
  (let [last-expr (last body)

        [body catch]
        (if (and (seq? last-expr) (= 'catch (first last-expr)))
          [(butlast body) last-expr]
          [body nil])]

    ;; FIXME: -> here will always return a promise so shouldn't be necessary to add js hint?
    `(-> ~thenable
         ~@(when (seq body)
             [`(.then (fn [~name] ~@body))])
         ~@(when catch
             (let [[name & body] catch]
               [`(.catch (fn [~name] ~@body))]
               )))))