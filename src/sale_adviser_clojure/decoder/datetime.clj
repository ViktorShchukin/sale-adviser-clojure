(ns sale-adviser-clojure.decoder.datetime
  (:require
    [java-time.api :as jt])
  (:import
    (java.util Date)
    (java.time LocalDateTime ZoneId)
    (java.time.format DateTimeFormatter)))


;;todo Maybe use java.time.LocalDateTime/parse
;; because we lose the Exception data with java-time.api
(defn from-string
  ^LocalDateTime
  ([^String s] (from-string s (jt/formatter "yyyy-MM-dd'T'HH:mm:ss'Z'")))
  ([^String s ^DateTimeFormatter formatter]
     (try
      (LocalDateTime/parse s formatter)
      (catch Exception e
        ;;todo finish this handler of error. Need to implement structure using the hash-map
        (-> (.getMessage e)
            (str )
            (ex-info  {})
            (throw ))))))

(defn from-java.util.Date
  ^LocalDateTime
  [^Date date]
  (try
    (LocalDateTime/ofInstant (.toInstant date) (ZoneId/of "UTC"))
    (catch Exception e
      ;;todo finish this handler of error. Need to implement structure using the hash-map
      (-> (.getMessage e)
          (str )
          (ex-info  {})
          (throw )))))