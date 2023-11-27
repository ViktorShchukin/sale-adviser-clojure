(ns sale-adviser-clojure.handler
  (:require
    [compojure.core :refer [defroutes GET POST PUT DELETE]]
    [compojure.route :as route]
    [ring.util.response :refer [resource-response response]]
    [ring.middleware.resource :refer [wrap-resource]]
    [ring.middleware.content-type :refer [wrap-content-type]]
    [ring.middleware.json :refer [wrap-json-response]]
    [sale-adviser-clojure.product :as product]
    [sale-adviser-clojure.sale :as sale]
    [sale-adviser-clojure.prediction :as prediction]))

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
           (GET "/dictionary/product/:productId" [productId] (product/get-product-by-id productId))
           ;todo unpack request and get body
           (POST "/dictionary/product" request (product/insert-product request))
           (PUT "/dictionary/product/:productId" [productId] (product/update-product productId))
           (DELETE "/dictionary/product/:productId" [productId] (product/delete-product productId))

           ;;routes to access sale entity
           (GET "/dictionary/product/:productId/sale" [productId] (sale/get-all-sale-by-productId productId))
           (GET "/dictionary/product/:productId/sale/:saleId" [productId saleId] (sale/get-sale-by-id saleId))
           ;todo unpack request and get body
           (POST "/dictionary/product/:productId/sale" request (sale/insert-sale request))
           (PUT "/dictionary/product/:productId/sale/:saleId" [productId saleId] (sale/update-sale saleId))
           (DELETE "/dictionary/product/:productId/sale/:saleId" [productId saleId] (sale/delete-sale saleId))

           ;;routes to access prediction
           ;range - It's count of mounts in which period we need to do prediction
           (GET "/dictionary/product/:productId/prediction/:range" [productId range] (prediction/get-prediction productId range))

           ;;the last route if other didn't match
           (route/not-found (get-index))
           )

(def app
  (-> app-routes
      (wrap-resource "public")
      (wrap-content-type)
      (wrap-json-response)))