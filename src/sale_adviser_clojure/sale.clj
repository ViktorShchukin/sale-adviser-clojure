(ns sale-adviser-clojure.sale
  (:require
    [next.jdbc :as jdbc]
    [next.jdbc.result-set :as rs]))

(def db-spec {:dbtype "postgresql"
              :dbname "sale_adviser"
              :user "anorisno"
              :password "GYAGG"})

(def ds (jdbc/get-datasource db-spec))

(def ds-opts (jdbc/with-options ds {:builder-fn rs/as-unqualified-lower-maps}))
(def test-sale {:id 1
                :product_id 2
                :quantity 3
                :total_value 5
                :date "01-12-2022"})

(defn get-all-sale-by-productId
  "get all sale by productId from db"
  [productId]
  (let [stm "select * from sale where product_id=?"
        product_uuid (parse-uuid productId)]
    (jdbc/execute! ds-opts [stm product_uuid])))

(defn get-sale-by-id
  [id]
  (let [stm "select * from sale where id=?"
        uuid (parse-uuid id)]
    (jdbc/execute! ds-opts [stm uuid])))

;;todo if sale id is nil it need to be created and be passed in statement
(defn insert-sale
  "if sale ID is nil it will be created"
  [productId {:keys [id product_id quantity total_sum date] }]
  (let [stm "insert into sale(id, product_id, quantity, total_sum, date) values(?, ?, ?, ?, ?)"
        uuid (parse-uuid id)
        product_uuid (parse-uuid productId)]
    (jdbc/execute! ds-opts [stm uuid product_uuid quantity total_sum date])))

(defn update-sale
  [id {:keys [product_id quantity total_sum date]}]
  (let [stm "update sale set product_id=?, quantity=?, total_sum=?, date=? where id=?"
        uuid (parse-uuid id)
        product_uuid (parse-uuid product_id)]
    (jdbc/execute! ds-opts [stm product_uuid quantity total_sum date uuid])))

(defn delete-sale
  [id]
  (let [stm "delete from sale where id=?"
        uuid (parse-uuid id)]
    (jdbc/execute! ds-opts [stm uuid])))