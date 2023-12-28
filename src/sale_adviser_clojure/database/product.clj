(ns sale-adviser-clojure.database.product
  (:require
    [next.jdbc :as jdbc]
    [next.jdbc.result-set :as rs]))

;;todo need to handle Exceptions from jdbc/execute
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
  []
  (jdbc/execute! ds-opts ["select * from product"]))

(defn get-all-product-where-name-like
  "get all product from db"
  [product-name]
  (let [stm "select * from product where name like ?"
        name-like (str "%" product-name "%")]
    (jdbc/execute! ds-opts [stm name-like])))



(defn get-product-by-id
  [id]
  (let [stm "select * from product where id=?"
        ;uuid (parse-uuid id)
        ]
    (jdbc/execute! ds-opts [stm id])))

;;todo if product id is nil it need to be created and be passed in statement
(defn insert-product
  "if product ID is nil it will be created"
  [{:keys [id name]}]
  (let [stm "insert into product(id, name) values (?,?)"
        uuid (case id
               nil (random-uuid)
               id)]
    (jdbc/execute! ds-opts [stm uuid name])))

(defn update-product
  [id {:keys [name]}]
  (let [stm "update product set name=? where id=?"]
    (jdbc/execute! ds-opts [stm name id])))

(defn delete-product
  [id]
  (let [stm "delete from product where id=?"]
    (jdbc/execute! ds-opts [stm id])))


;;todo maybe should return Product object instead of :id
(defn get-id-by-name
  "return nil or ^String"
  [name]
  (let [stm "select id from product where name=?"
        response (jdbc/execute-one! ds-opts [stm name])]
    (case response
      nil nil
      (response :id))))


(defn get-id-by-name-or-insert
  [name]
  (let [response (get-id-by-name name)]
    (case response
      ;;todo strange construction. Maybe jdbc can return what he inserts
      ;;todo need to handle Exceptions from jdbc
      nil (do (insert-product {:name name}) (get-id-by-name name))
      response)))