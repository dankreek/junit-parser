(ns user
  (require [clojure.xml :as xml]
           [clojure.pprint :refer :all]
           [junit-parser.core :as core]))

(def test-xml (xml/parse "dev-resources/tests.xml"))

(defn get-failures [abs-path]
  "Output the relative path to all test case failures. abs-path contains
  the absolute path (ending in a '/' to strip away from the test case
  filenames."
  (->> test-xml
       core/failures
       core/test-case-names
       (core/strip-absolute-paths abs-path)))