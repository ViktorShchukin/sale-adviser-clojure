(ns sale-adviser-clojure.group-prediction-file
  (:require
    [sale-adviser-clojure.database.product-group :as product-group]
    [clojure.data.csv :as csv]
    [sale-adviser-clojure.calculator :as calculator]
    [clojure.java.io :as io])
  (:import
    (java.io File)))



(defn get-predictions
  "get predictions for coll of products of group"
  [products prediction-date]
  (map #(calculator/get-prediction % prediction-date) (map :product-id products)))

(defn equal-product-id?
  [p1 p2]
  (= p1 p2))

(defn assoc-prediction-to-product
  [prediction product]
  (if (equal-product-id? (:product-id prediction) (:product-id product))
    (assoc product :prediction (:value prediction))))

(defn assoc-prediction-to-products
  [prediction products]
  (first (filter #(not (nil? %)) (map #(assoc-prediction-to-product prediction %) products))))


(defn assoc-predictions-and-products
  [products predictions]
  (map #(assoc-prediction-to-products % products) predictions))

(defn get-products-with-predictions
  [group-id prediction-date]
  (let [products (product-group/get-products-and-custom-value-by-group-id group-id)
        predictions (get-predictions products prediction-date)]
    (assoc-predictions-and-products products predictions)))

(defn product-to-vector
  [prepared-product]
  [(:name prepared-product) (:prediction prepared-product) (:custom-value prepared-product)])

(defn products-to-vector
  [prepared-products]
  (vec (map product-to-vector prepared-products)))


(defn write-data-to-csv
  [writer predicted-products]
  (csv/write-csv writer (products-to-vector predicted-products) :separator \;))

(defn get-file
  [group-id prediction-date]
  (let [prepared-products (get-products-with-predictions group-id prediction-date)
        file (File/createTempFile "prediction" ".csv")]
    (with-open [writer (io/writer file)]
      (write-data-to-csv writer prepared-products))
    file))