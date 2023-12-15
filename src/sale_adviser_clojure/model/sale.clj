(ns sale-adviser-clojure.model.sale
  (:import
    (java.time LocalDateTime)
    (java.util UUID)))

;;todo maybe use zoned datetime?
(defrecord Sale
  [^UUID id
   ^UUID product-id
   ^Integer quantity
   ^Integer total-sum
   ^LocalDateTime date])


