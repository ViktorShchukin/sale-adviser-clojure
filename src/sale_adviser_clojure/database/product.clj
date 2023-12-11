(ns sale-adviser-clojure.database.product
  (:require
    [next.jdbc :as jdbc]
    [next.jdbc.result-set :as rs]))


;;todo create a new namespace which will handle the db access. files like "sale" or "product" should handle the model of app
;;todo maybe I should get db-spec from config?
;;todo get user and password from environment variables
(def db-spec {:dbtype "postgresql"
              :dbname "sale_adviser"
              :user "anorisno"
              :password "GYAGG"})

(def ds (jdbc/get-datasource db-spec))

(def ds-opts (jdbc/with-options ds {:builder-fn rs/as-unqualified-lower-maps}))

;;(jdbc/execute! ds ["select * from product"])

;todo implement logic inside func
(defn get-all-product
  "get all product from db"
  []
  (jdbc/execute! ds-opts ["select * from product"]))

(defn get-product-by-id
  [id]
  (let [stm "select * from product where id=?"
        uuid (parse-uuid id)]
    (jdbc/execute! ds-opts [stm uuid])))

;;todo if product id is nil it need to be created and be passed in statement
(defn insert-product
  "if product ID is nil it will be created"
  [{:keys [id name]}]
  (let [stm "insert into product(id, name) values (?,?)"
        uuid (parse-uuid id)]
    (jdbc/execute! ds-opts [stm uuid name])))

(defn update-product
  [id {:keys [name]}]
  (let [stm "update product set name=? where id=?"
        uuid (parse-uuid id)]
    (jdbc/execute! ds-opts [stm name uuid])))

(defn delete-product
  [id]
  (let [stm "delete from product where id=?"
        uuid (parse-uuid id)]
    (jdbc/execute! ds-opts [stm uuid])))
