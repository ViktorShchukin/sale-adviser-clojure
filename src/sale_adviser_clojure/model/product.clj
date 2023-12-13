(ns sale-adviser-clojure.model.product
  (:import (java.util UUID)))

(defrecord Product
  [^UUID id
   ^String name])




