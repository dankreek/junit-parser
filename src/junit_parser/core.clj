(ns junit-parser.core
  (:gen-class)
  (:require [clojure.string :as string]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Utility functions

(defn is-failure? [test-result]
  (= 1 (count (filter #(= :failure (:tag %)) (:content test-result)))))

(defn test-case-name [test-result]
  (let [attrs (:attrs test-result)
        classname (:classname attrs)
        name (:name attrs)]
    (str classname "/" name)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Public

(defn test-cases [junit-xml]
  "Get all test cases from a test.xml file."
  (filter #(= :testcase (:tag %)) (:content junit-xml)))

(defn failures [junit-xml] ;; TODO : Schematize this
  "Gather all test failures from JUnit test XML."
  (filter is-failure? (test-cases junit-xml)))

(defn test-case-names [test-cases]
  "Display all test names in a list of test cases"
  (map test-case-name test-cases))

(defn strip-absolute-paths [base-path case-names]
  "Strip the absolute paths from the base name of a list of test test cases
  containing a full absolute path."
  {:pre [(seq? case-names) (string? base-path)]}
  (let [re (re-pattern base-path)
        xform #(last (string/split % re))]
    (map xform case-names)))

(defn tests-by-name
  "Get all tests by their filename, ie 'some_test.rb'."
  [test-cases name]
  (filter #(= name (get-in % [:attrs :name])) test-cases))

(defn failure-output [test-case]
  "Get the output from a failed test."
  (let [failure (first (filter #(= :failure (:tag %)) (:content test-case)))]
    (get-in failure [:attrs :message])))