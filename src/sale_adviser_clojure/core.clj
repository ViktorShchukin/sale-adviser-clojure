(ns sale-adviser-clojure.core
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty])
  (:gen-class))

(defroutes app-routes
           (GET "/" [] "Hello, World!")
           (route/not-found "Not Found"))

(defn hendler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World"})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
