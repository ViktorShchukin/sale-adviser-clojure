(ns sale-adviser-clojure.product)


(def test-product {:id 1
                   :name "first"})

;todo implement logic inside func
(defn get-all-product
  "get all product from db"
  []
  (list test-product))

(defn get-product-by-id
  [id]
  (list test-product))

(defn insert-product
  "if product ID is nil it will be created"
  [request-body]
  (list test-product))

(defn update-product
  [id]
  (list test-product))

(defn delete-product
  [id]
  (list test-product))

