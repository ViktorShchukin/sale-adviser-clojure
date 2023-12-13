(ns sale-adviser-clojure.decoder.uuid
  (:import
    (java.util UUID)))

;;todo it's need to check result of parse-uuid and catch Exceptions
(defn from-string
  ^UUID [^String s]
  (try
    (UUID/fromString s)
    (catch Exception e
      ;;todo finish this handler of error. Need to implement structure using the hash-map
      (throw (ex-info (str (.getMessage e)) {})))
    ))


(comment
  (def some-error (sale-adviser-clojure.decoder.uuid/from-string "some"))
  (try
    (sale-adviser-clojure.decoder.uuid/from-string "a48b2fdb-8308-469b-9456-b007d4755bss")
    (catch clojure.lang.ExceptionInfo e
      (.getMessage e))))