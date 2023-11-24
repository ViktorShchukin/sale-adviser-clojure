(ns sale-adviser-clojure.config
  (:require
    [clojure.edn :as edn]
    [clojure.java.io :as io]))

(defn read-config
  "return a config map from resources/config.edn"
  [& args]
  (edn/read-string (slurp (io/resource "config.edn"))))


