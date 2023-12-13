(ns sale-adviser-clojure.handler
  (:require
    [compojure.core :refer [defroutes GET POST PUT DELETE]]
    [compojure.route :as route]
    [ring.util.response :refer [resource-response response]]
    [ring.middleware.resource :refer [wrap-resource]]
    [ring.middleware.content-type :refer [wrap-content-type]]
    [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
    [sale-adviser-clojure.database.product :as product]
    [sale-adviser-clojure.database.sale :as sale]
    [sale-adviser-clojure.model.prediction :as prediction]
    [sale-adviser-clojure.decoder.uuid :as id-decoder])
  (:import (clojure.lang ExceptionInfo)))

(defn get-index
  []
  (-> (resource-response "index1.html" {:root "public"})
      (assoc-in [:headers "Content-Type"] "text/html")))

(defroutes app-routes
           ;;routs for front app. It's spa here. Just need to serve index to all app routs
           (GET "/" [] (get-index))
           (GET "/search-product" [] (get-index))

           ;;routes to access product entity
           (GET "/dictionary/product" [] (response (product/get-all-product)))
           (GET "/dictionary/product/:productId" [productId] (response (product/get-product-by-id (id-decoder/from-string productId))))
           (POST "/dictionary/product" [:as {body :body}] (response (product/insert-product body)))
           (PUT "/dictionary/product/:productId" [productId :as {body :body}] (response (product/update-product (id-decoder/from-string productId) body)))
           (DELETE "/dictionary/product/:productId" [productId] (response (product/delete-product (id-decoder/from-string productId))))

           ;;routes to access sale entity
           (GET "/dictionary/product/:productId/sale" [productId] (response (sale/get-all-sale-by-productId (id-decoder/from-string productId))))
           (GET "/dictionary/product/:productId/sale/:saleId" [productId saleId] (response (sale/get-sale-by-id (id-decoder/from-string productId))))
           ;todo unpack request and get body
           (POST "/dictionary/product/:productId/sale" [productId :as {body :body}] (response (sale/insert-sale (id-decoder/from-string productId) body)))
           (PUT "/dictionary/product/:productId/sale/:saleId" [productId saleId :as {body :body}] (response (sale/update-sale (id-decoder/from-string productId) body)))
           (DELETE "/dictionary/product/:productId/sale/:saleId" [productId saleId] (response (sale/delete-sale (id-decoder/from-string productId))))

           ;;routes to access prediction
           ;range - It's count of mounts in which period we need to do prediction
           (GET "/dictionary/product/:productId/prediction/:range" [productId range] (response (prediction/get-prediction (id-decoder/from-string productId) range)))

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
      (catch clojure.lang.ExceptionInfo e
        {:status 400 :body (str "Invalid data: " (.getMessage e))})
      (catch Exception e
        {:status 500 :body "something get wrong"}))))

(def app
  (-> app-routes
      (wrap-resource "public")
      (wrap-content-type)
      (wrap-json-response)
      (wrap-json-body {:keywords? true})
      (wrap-exception-handling)))

