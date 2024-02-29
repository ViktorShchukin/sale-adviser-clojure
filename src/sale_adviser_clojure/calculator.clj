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
    (/ (- (:quantity before-last-record)
          ( :quantity last-record))
       (- (.toEpochSecond  (:sale-date before-last-record) (java.time.ZoneOffset/UTC))
          (.toEpochSecond (:sale-date last-record) (java.time.ZoneOffset/UTC))))))
    ;(- (before-last-record :quantity) (last-record :quantity))(/  (- (before-last-record :date) (last-record :date)))))




(defn calculate-prediction
  [product-id prediction-date]
  (let [sales (sale/get-all-sale-by-product-id-grouped-and-ordered-by-date product-id)]
    (+ (:quantity (last sales))
       (* (derivative-of-last-two sales)
          (- (.toEpochSecond prediction-date (java.time.ZoneOffset/UTC))
             (.toEpochSecond (:sale-date (last sales)) (java.time.ZoneOffset/UTC)))))))

(defn calculate-from-now
  [product-id prediction-date]
  (- (calculate-prediction product-id prediction-date)
     (calculate-prediction product-id (java.time.LocalDateTime/now))))


(defn get-prediction
  [product-id prediction-date]
  (Prediction. product-id (int (calculate-from-now product-id prediction-date)) (.toString prediction-date)))

