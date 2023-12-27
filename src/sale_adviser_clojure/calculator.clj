(ns sale-adviser-clojure.calculator
  (:require
    [sale-adviser-clojure.database.sale :as sale])
  (:import
    (sale_adviser_clojure.model.prediction Prediction))
  )

(defn derivative-of-last-two
  [coll]
  (let [last-record (last coll)
        before-last-record (last (butlast coll))]
    (/ (- (before-last-record :quantity) (last-record :quantity)) (- (.getTime (before-last-record :sale-date)) (.getTime (last-record :sale-date))))))
    ;(- (before-last-record :quantity) (last-record :quantity))(/  (- (before-last-record :date) (last-record :date)))))

(defn calculate-prediction
  [product-id prediction-date]
  (let [sales (sale/get-all-sale-by-product-id-order-by-date product-id)]
    (+ ((last sales) :quantity) (* (derivative-of-last-two sales) (- (.getTime prediction-date) (.getTime ((last sales) :sale-date)))))))

(defn get-prediction
  [product-id prediction-date]
  (Prediction. product-id (calculate-prediction product-id prediction-date) prediction-date))