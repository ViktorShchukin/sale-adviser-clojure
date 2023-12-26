(ns sale-adviser-clojure.database.sale
  (:require
    [next.jdbc :as jdbc]
    [next.jdbc.result-set :as rs]
    [java-time.api :as jt]))


;;todo create a new namespace which will handle the db access. files like "sale" or "product" should handle the model of app
;;todo maybe I should get db-spec from config?
;;todo get user and password from environment variables
(def db-spec {:dbtype "postgresql"
              :dbname "sale_adviser"
              :user "anorisno"
              :password "GYAGG"})

(def ds (jdbc/get-datasource db-spec))

(def ds-opts (jdbc/with-options ds {:builder-fn rs/as-unqualified-kebab-maps}))
(def test-sale {:id 1
                :product_id 2
                :quantity 3
                :total_value 5
                :date "01-12-2022"})

(defn get-all-sale-by-productId
  "get all sale by productId from db"
  [productId]
  (let [stm "select * from sale where product_id=?"
        ;product_uuid (parse-uuid productId)
        ]
    (jdbc/execute! ds-opts [stm productId])))

(defn get-sale-by-id
  [id]
  (let [stm "select * from sale where id=?"
        uuid (parse-uuid id)]
    (jdbc/execute! ds-opts [stm uuid])))

;;todo if sale id is nil it need to be created and be passed in statement
(defn insert-sale
  "if sale ID is nil it will be created"
  [{:keys [id product-id quantity total-sum date] }]
  (let [stm "insert into sale(id, product_id, quantity, total_sum, sale_date) values(?, ?, ?, ?, ?)"
        uuid (case id
               nil (random-uuid)
               id)]
    (try
       (jdbc/execute! ds-opts [stm uuid product-id quantity total-sum date])
       (catch Exception e
         ;;todo put message about exception into log
         nil))))

(defn update-sale
  [id {:keys [product_id quantity total_sum date]}]
  (let [stm "update sale set product_id=?, quantity=?, total_sum=?, date=? where id=?"
        uuid (parse-uuid id)
        product_uuid (parse-uuid product_id)
        pars_date (jt/local-date-time date)]
    (jdbc/execute! ds-opts [stm product_uuid quantity total_sum pars_date uuid])))

(defn delete-sale
  [id]
  (let [stm "delete from sale where id=?"
        uuid (parse-uuid id)]
    (jdbc/execute! ds-opts [stm uuid])))
