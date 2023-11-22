(ns sale-adviser-clojure.core
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :refer [resource-response]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.content-type :refer [wrap-content-type]])
  (:gen-class))

(defroutes app-routes
           (GET "/" [] (assoc-in (resource-response "index1.html" {:root "public"}) [:headers "Content-Type"] "text/html"))
           (GET "/search-product" [] (assoc-in (resource-response "index1.html" {:root "public"}) [:headers "Content-Type"] "text/html"))
           (route/not-found "Not Found")
           )
(defn hendler [request]
  (assoc-in (resource-response "index1.html" {:root "public"}) [:headers "Content-Type"] "text/html"))
(def app
  (-> app-routes
      (wrap-resource "public")
      (wrap-content-type)))



(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (jetty/run-jetty app  {:port 3000 :join? false}))


