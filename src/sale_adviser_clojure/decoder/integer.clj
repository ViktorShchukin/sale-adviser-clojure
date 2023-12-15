(ns sale-adviser-clojure.decoder.integer)


(defn from-string
  ^Integer
  [^String s]
  (try
    (Integer/parseInt s)
    (catch Exception e
      ;;todo finish this handler of error. Need to implement structure using the hash-map
      (-> (.getMessage e)
          (str )
          (ex-info  {})
          (throw )))))
