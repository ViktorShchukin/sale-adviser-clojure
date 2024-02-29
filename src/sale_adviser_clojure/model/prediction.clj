(ns sale-adviser-clojure.model.prediction
  (:require
    [sale-adviser-clojure.database.sale :as sale])
  (:import (java.util UUID)))

(defrecord Prediction
  [^UUID product-id
   ^Float value
   ^String range])

;(def test-prediction {:productId 1
;                      :value 4
;                      :range 7})


;(defn table-func
;  [productId]
;  (let [db-response (sale/get-all-sale-by-productId productId)]
;    ))

;(defn get-prediction
;  [productId range]
;  )

