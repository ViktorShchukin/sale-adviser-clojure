(ns sale-adviser-clojure.core
  (:require
            [ring.adapter.jetty :as jetty]
            [sale-adviser-clojure.handler :as handler]
            [sale-adviser-clojure.config :as conf])
  (:gen-class))

(defn -main
  ""
  [& args]
  (let [config (conf/read-config)]
    (jetty/run-jetty handler/app {:port 3000 :join? false})))

(comment
  (def server (-main))
  (.stop server)
  (.start server)
  )

