(ns sale-adviser-clojure.data-parser
  (:require
    [clojure.string :as str]
    [java-time.api :as jt]))

(defrecord Sale
  [product-name date quantity price])

(defn parse-string-sale
  [string divider]
  (let [x (str/split string divider)]
    (->Sale (x 0) (x 1) (x 2) (x 3))))



