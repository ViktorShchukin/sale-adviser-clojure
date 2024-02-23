(ns sale-adviser-clojure.database.product-group
  (:require
    [next.jdbc :as jdbc]
    [next.jdbc.result-set :as rs]
    [sale-adviser-clojure.decoder.uuid :as decode-uuid])
  (:import
    (java.time LocalDateTime ZoneId)))

;;todo need to handle Exceptions from jdbc/execute
;;todo maybe I should get db-spec from config?
;;todo get user and password from environment variables
(def db-spec {:dbtype "postgresql"
              :dbname "sale_adviser"
              :user "anorisno"
              :password "GYAGG"})

(def ds (jdbc/get-datasource db-spec))

(def ds-opts (jdbc/with-options ds {:builder-fn rs/as-unqualified-kebab-maps}))

(defn get-all-groups-of-product
  []
  (jdbc/execute! ds-opts ["select * from product_groups"]))

(comment
  (defn get-all-product-where-name-like
  "get all product from db"
  [product-name]
  (let [stm "select * from product where name like ?"
        name-like (str "%" product-name "%")]
    (jdbc/execute! ds-opts [stm name-like]))))



(defn get-group-of-product-by-id
  [id]
  (let [stm "select * from product_groups where id=?"
        ;uuid (parse-uuid id)
        ]
    (jdbc/execute-one! ds-opts [stm id])))

;;todo if product id is nil it need to be created and be passed in statement
;;todo in databese.product wrong func. It can't work becouse it not validate id
(defn create-new-group-of-product
  "if product ID is nil it will be created"
  [{:keys [id name]}]
  (let [stm "insert into product_groups(id, creation_date, name) values (?,?,?)"
        uuid (case id
               nil (random-uuid)
               (decode-uuid/from-string id))
        date (LocalDateTime/now (ZoneId/of "UTC"))]
    (jdbc/execute! ds-opts [stm uuid date name])))

;;todo what i need to update? Only name?
(defn update-group-of-product
  [id {:keys [name]}]
  (let [stm "update product_groups set name=? where id=?"]
    (jdbc/execute! ds-opts [stm name id])))

(defn delete-group-of-product-by-id
  [id]
  (let [stm "delete from product_groups where id=?"]
    (jdbc/execute! ds-opts [stm id])))

(defn get-all-product-of-group-by-group-id
  [group-id]
  (let [stm "select p.* from product p join product_and_groups pg on p.id=pg.product_id where pg.group_id = ?"]
    (jdbc/execute! ds-opts [stm group-id])))

(defn get-product-by-group-id-product-id
  [group-id product-id]
  (let [stm "select * from product_and_groups where group_id=? and product_id=?"]
    (jdbc/execute! ds-opts [stm group-id product-id])))

(defn insert-product-in-group
  [group-id product-id]
  (let [stm "insert into product_and_groups(group_id, product_id) values (?,?)"]
    (jdbc/execute! ds-opts [stm group-id product-id])))

(defn delete-product-from-group
  [group-id product-id]
  (let [stm "delete from product_and_groups where group_id=? and product_id=?"]
    (jdbc/execute! ds-opts [stm group-id product-id])))


(defn get-custom-value
  [group-id product-id]
  (let [stm "select * from product_and_groups where group_id=? and product_id=?"]
    (jdbc/execute-one! ds-opts [stm group-id product-id])))


(defn update-custom-value
  [group-id product-id {:keys [custom-value]}]
  (let [stm "update product_and_groups set custom_value=? where group_id=? and product_id=?"]
    (jdbc/execute-one! ds-opts [stm custom-value group-id product-id])))

(defn get-products-and-custom-value-by-group-id
  [group-id]
  (let [stm "select pag.product_id, p.name , pag.custom_value
                from product_and_groups pag join product p ON pag.product_id = p.id
                                            join product_groups pg on pag.group_id = pg.id
                where pag.group_id = ?;"]
    (jdbc/execute! ds-opts [stm group-id])))


