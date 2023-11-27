(ns sale-adviser-clojure.sale)

(def test-sale {:id 1
                :product_id 2
                :quantity 3
                :total_value 5
                :date "01-12-2022"})

(defn get-all-sale-by-productId
  "get all product from db"
  [productId]
  (list test-sale))

(defn get-sale-by-id
  [id]
  (list test-sale))

(defn insert-sale
  "if product ID is nil it will be created"
  [body & arg]
  (list test-sale))

(defn update-sale
  [id]
  (list test-sale))

(defn delete-sale
  [id]
  (list test-sale))