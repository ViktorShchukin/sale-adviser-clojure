(ns sale-adviser-clojure.product)


(def test-product {:id 1
              :name "first"
              })
;todo implement logic inside func
(defn get-all-product
  "get all product from db"
  [& arg]
  (list test-product))

(defn get-product-by-id
  ""
  [id & arg]
  (list test-product))

(defn insert-product
  "if product ID is nil it will be created"
  [id name & arg]
  (list test-product))

(defn update-product
  ""
  [id & arg]
  (list test-product))

(defn delete-product
  ""
  [id & arg]
  (list test-product))