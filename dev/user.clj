(ns user
  (require [clojure.xml :as xml]
           [clojure.pprint :refer :all]
           [junit-parser.core :as core]))

(def test-xml (xml/parse "dev-resources/tests.xml"))

(defn get-failures [abs-path]
  (->> test-xml
       core/failures
       core/test-case-names
       (core/strip-absolute-paths abs-path)))