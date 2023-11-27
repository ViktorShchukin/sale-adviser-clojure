(ns sale-adviser-clojure.prediction)

(def test-prediction {:productId 1
                      :value 4
                      :range 7})

(defn get-prediction
  [productId range]
  test-prediction)