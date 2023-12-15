(ns sale-adviser-clojure.decoder.sale
  (:require
    [clojure.string :as str]
    [clojure.data.csv :as csv]
    [clojure.java.io :as io]
    [sale-adviser-clojure.model.sale]
    [sale-adviser-clojure.decoder.integer :as decode-int]
    [sale-adviser-clojure.decoder.float :as decode-float]
    [sale-adviser-clojure.decoder.datetime :as decode-datetime]
    [sale-adviser-clojure.database.product :as product])
  (:import (sale_adviser_clojure.model.sale Sale)))

(comment
  (def some-string ["Дезинфицирующее средство \"Aqualeon\" жидкое 30л с дыш.кр" "26.12.2022 12:57:52" "6" "1680"])
  (Sale. (some-string 0) (some-string 1) (some-string 2) (some-string 3) (some-string 0))
)
(defn from-json
  [string])

(defn validate-csv-row
  [coll]                                                    ;this coll is vector that produced be the read-cav
  (vector (coll 0) (decode-datetime/from-string (coll 1) (java-time.api/formatter "dd.MM.yyyy' 'HH:mm:ss")) (decode-float/from-string (coll 2)) (decode-float/from-string (coll 3))))

(defn convert-csv-row-to-Sale
  [coll]
  (Sale. (random-uuid) (product/get-id-by-name-or-insert (coll 0)) (coll 2) (* (coll 2) (coll 3)) (coll 1)))

(defn from-csv
  [reader]
  (->> (csv/read-csv reader {:separator \;})
       (map validate-csv-row )
       (map convert-csv-row-to-Sale )
       ))

;;todo delete this later
(defn parse-csv-old
  [file-name]
  (lazy-seq (with-open [open-file (io/reader file-name)]
    ;;todo check can we undo doall. Can it work?
     (csv/read-csv open-file {:separator \;}))))

;;todo delete this later
;;todo Maybe use lazy-seq. How can i do
(defn from-csv-old
  ([file-name]
   (let [sale-seq (parse-csv file-name)]
     (lazy-seq ()))))