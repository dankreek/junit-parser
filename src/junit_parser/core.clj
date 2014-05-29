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

(defn failures [junit-xml] ;; TODO : Schematize this
  "Gather all test failures from JUnit test XML."
  (filter is-failure? (:content junit-xml)))

(defn test-case-names [test-cases]
  "Display all test names in a list of test cases"
  (map test-case-name test-cases))

(defn strip-absolute-paths [base-path case-names]
  (let [re (re-pattern base-path)
        xform #(last (string/split % re))]
    (map xform case-names)))

