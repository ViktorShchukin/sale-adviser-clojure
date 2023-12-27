(ns sale-adviser-clojure.handler
  (:require
    [clojure.java.io :as io]
    [compojure.core :refer [defroutes GET POST PUT DELETE]]
    [compojure.route :as route]
    [ring.util.response :refer [resource-response response]]
    [ring.middleware.resource :refer [wrap-resource]]
    [ring.middleware.content-type :refer [wrap-content-type]]
    [ring.middleware.multipart-params :refer [wrap-multipart-params]]
    [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
    [sale-adviser-clojure.database.product :as product]
    [sale-adviser-clojure.database.sale :as sale]
    [sale-adviser-clojure.model.prediction :as prediction]
    [sale-adviser-clojure.decoder.uuid :as id-decoder]
    [sale-adviser-clojure.decoder.sale :as decode-sale]
    [sale-adviser-clojure.calculator :as calculator])
  (:import
    (clojure.lang ExceptionInfo)
    (java.io FileReader BufferedReader)))

(defn upload-file
  [file]
  (with-open [reader (io/reader (file :tempfile))]
    (doall (filter #(not (nil? %)) (map sale/insert-sale (decode-sale/from-csv reader))))))

(defn get-index
  []
  (-> (resource-response "index1.html" {:root "public"})
      (assoc-in [:headers "Content-Type"] "text/html")))

;;todo wrap input into decoder !!not all func receive right arguments from request now especially routs for sale
(defroutes app-routes
           ;;routs for front app. It's spa here. Just need to serve index to all app routs
           (GET "/" [] (get-index))
           (GET "/search-product" [] (get-index))

           ;;routes for file upload with sales data
           ;;todo need to return count of updated rows but now returns count of evalueted rows
           ;;todo need to return Int
           (POST "/dictionary/sale/file/upload" [:as {params :params}] (response {:count-updated-row (count (upload-file (get params "file")))}))

           ;;routes to access product entity
           (GET "/dictionary/product" [] (response (product/get-all-product)))
           (GET "/dictionary/product/:productId" [productId] (response (product/get-product-by-id (id-decoder/from-string productId))))
           (POST "/dictionary/product" [:as {body :body}] (response (product/insert-product body)))
           (PUT "/dictionary/product/:productId" [productId :as {body :body}] (response (product/update-product (id-decoder/from-string productId) body)))
           (DELETE "/dictionary/product/:productId" [productId] (response (product/delete-product (id-decoder/from-string productId))))

           ;;routes to access sale entity
           (GET "/dictionary/product/:productId/sale" [productId] (response (sale/get-all-sale-by-productId (id-decoder/from-string productId))))
           (GET "/dictionary/product/:productId/sale/:saleId" [productId saleId] (response (sale/get-sale-by-id (id-decoder/from-string productId))))
           ;todo need to decode body
           (POST "/dictionary/product/:productId/sale" [productId :as {body :body}] (response (sale/insert-sale  body)))
           (PUT "/dictionary/product/:productId/sale/:saleId" [productId saleId :as {body :body}] (response (sale/update-sale (id-decoder/from-string productId) body)))
           (DELETE "/dictionary/product/:productId/sale/:saleId" [productId saleId] (response (sale/delete-sale (id-decoder/from-string productId))))

           ;;routes to access prediction
           ;range - It's count of mounts in which period we need to do prediction
           (GET "/dictionary/product/:productId/prediction/:range" [productId range] (response (calculator/get-prediction (id-decoder/from-string productId) range)))

           ;;the last route if other didn't match
           (route/not-found (get-index))
           )

(defn wrap-exception-handling
  [handler]
  (fn [request]
    (try
      (handler request)
      ;;todo handel clojure.lang.ExceptionInfo
      (catch ExceptionInfo e
        (case ((ex-data e) :type)
          :cannotCast ))
      (catch ExceptionInfo e
        {:status 400 :body (str "Invalid data: " (.getMessage e))})
      (catch Exception e
        {:status 500 :body (.getMessage e)}))))

(def app
  (-> app-routes
      (wrap-resource "public")
      (wrap-content-type)
      (wrap-json-response)
      (wrap-json-body {:keywords? true})
      (wrap-exception-handling)
      (wrap-multipart-params)
      ))

