(ns sale-adviser-clojure.database.stock-balance
  (:require
    [next.jdbc :as jdbc]
    [next.jdbc.result-set :as rs]
    [java-time.api :as jt]
    [sale-adviser-clojure.decoder.sale :as decode-sale]
    [sale-adviser-clojure.decoder.datetime :as decode-date]))


;;todo create a new namespace which will handle the db access. files like "sale" or "product" should handle the model of app
;;todo maybe I should get db-spec from config?
;;todo get user and password from environment variables

;;todo add implementation of this!!!
(def db-spec {:dbtype "postgresql"
              :dbname "sale_adviser"
              :user "anorisno"
              :password "GYAGG"})

(def ds (jdbc/get-datasource db-spec))

(def ds-opts (jdbc/with-options ds {:builder-fn rs/as-unqualified-kebab-maps}))

(defn get-all-by-product-id
  [productId]
  (let [stm "select * from stock_balance where product_id=?"
        ;product_uuid (parse-uuid productId)
        ]
    (jdbc/execute! ds-opts [stm productId])))

(defn get-by-id
  [id]
  (let [stm "select * from stock_balance where id=?"
        ;;uuid (parse-uuid id)
        ]
    (jdbc/execute! ds-opts [stm id])))

(defn insert
  "if stock-balance ID is nil it will be created"
  [{:keys [id product-id quantity sb-timestamp] }]
  (let [stm "insert into stock_balance(id, product_id, quantity, sb_timestamp) values(?, ?, ?, ?)"
        uuid (case id
               nil (random-uuid)
               (parse-uuid id))
        parse-product-id (parse-uuid product-id)
        pars-timestamp (decode-date/from-string  sb-timestamp)]
    (try
      (jdbc/execute! ds-opts [stm uuid parse-product-id quantity pars-timestamp])
      (catch Exception e
        (-> (.getMessage e)
            (str )
            (ex-info  {})
            (throw ))))))

(defn my-update
  [id {:keys [product-id quantity sb-timestamp]}]
  (let [stm "update stock_balance set product_id=?, quantity=?, sb_timestamp=? where id=?"
        ;;uuid (parse-uuid id)
        product_uuid (parse-uuid product-id)
        pars_date (decode-date/from-string sb-timestamp)]
    (jdbc/execute! ds-opts [stm product_uuid quantity pars_date id])))

(defn delete
  [id]
  (let [stm "delete from stock_balance where id=?"
        ;;uuid (parse-uuid id)
        ]
    (jdbc/execute! ds-opts [stm id])))
