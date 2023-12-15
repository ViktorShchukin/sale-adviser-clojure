(ns sale-adviser-clojure.decoder.float)

(defn from-string
  ^Float
  [^String s]
  (try
    (Float/parseFloat s)
    (catch Exception e
      ;;todo finish this handler of error. Need to implement structure using the hash-map
      (-> (.getMessage e)
          (str )
          (ex-info  {})
          (throw )))))