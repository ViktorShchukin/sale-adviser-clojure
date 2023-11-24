(ns sale-adviser-clojure.handler
  (:require
    [compojure.core :refer [defroutes GET POST PUT DELETE]]
    [compojure.route :as route]
    [ring.util.response :refer [resource-response]]
    [ring.middleware.resource :refer [wrap-resource]]
    [ring.middleware.content-type :refer [wrap-content-type]]
    [sale-adviser-clojure.product :as product]))


(defroutes app-routes
           ;;routs for front app
           (GET "/" [] (-> (resource-response "index1.html" {:root "public"})
                           (assoc-in [:headers "Content-Type"] "text/html")))
           (GET "/search-product" [] (-> (resource-response "index1.html" {:root "public"})
                                         (assoc-in [:headers "Content-Type"] "text/html")))

           ;;routes to access product entity
           (GET "/dictionary/product" [] (product/get-all-product))
           (GET "/dictionary/product/:productId" [productId] (product/get-product-by-id productId))
           (POST "/dictionary/product" [id name] (product/insert-product id name)) ;todo unpack request and get body
           (PUT "/dictionary/product/:productId" [productId] (product/update-product productId))
           (DELETE "/dictionary/product/:productId" [productId] (product/delete-product productId))

           ;;routes to access sale entity
           (GET "/dictionary/product/:productId/sale" [productId saleId] {})
           (GET "/dictionary/product/:productId/sale/:saleId" [productId saleId] {})
           (POST "/dictionary/product/:productId/sale" request {})
           (PUT "/dictionary/product/:productId/sale/:saleId" [productId saleId] {})
           (DELETE "/dictionary/product/:productId/sale/:saleId" [productId saleId] {})

           ;;routes to access prediction
           (GET "/dictionary/product/:productId/prediction/:range" [productId range] {})

           ;;the last route if other don't match
           (route/not-found (-> (resource-response "index1.html" {:root "public"})
                                (assoc-in [:headers "Content-Type"] "text/html")))
           )

(def app
  (-> app-routes
      (wrap-resource "public")
      (wrap-content-type)))