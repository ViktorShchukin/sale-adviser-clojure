(ns sale-adviser-clojure.core
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :refer [file-response]])
  (:gen-class))

(defroutes app-routes
           (GET "/" [] "Hello, World!")
           (route/not-found "Not Found"))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World"})

(defn handler2 [request]
  (file-response "index1.html" {:root "static"}))



(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (jetty/run-jetty handler2  {:port 3000 :join? false}))
