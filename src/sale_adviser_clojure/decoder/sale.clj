(ns sale-adviser-clojure.decoder.sale
  (:require
    [clojure.string :as str]
    [clojure.data.csv :as csv]
    [clojure.java.io :as io])
  (:import (sale_adviser_clojure.sale Sale)))

(def some-string ["Дезинфицирующее средство \"Aqualeon\" жидкое 30л с дыш.кр" "26.12.2022 12:57:52" "6.000" "1,680.00"])
(Sale. (some-string 0) (some-string 1) (some-string 2) (some-string 3) (some-string 0))

(defn from-json
  [string])


(defn parse-csv
  [file-name]
  (with-open [open-file (io/reader file-name)]
    (doall (csv/read-csv open-file {:separator \;}))))

(defn from-cs
  ([file-name]
   (let [sale-seq (parse-csv file-name)])))