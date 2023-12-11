(ns sale-adviser-clojure.prediction
  (:require
    [sale-adviser-clojure.sale :as sale]))

(defrecord prediction
  [product-id value range])

(def test-prediction {:productId 1
                      :value 4
                      :range 7})


(defn table-func
  [productId]
  (let [db-response (sale/get-all-sale-by-productId productId)]
    ))

(defn get-prediction
  [productId range]
  )

