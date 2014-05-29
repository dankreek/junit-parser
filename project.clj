(defproject junit-parser "0.1.0-SNAPSHOT"
  :description "Parses a JUnit XML file."

  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.5.1"]]

  :main ^:skip-aot junit-parser.core

  :target-path "target/%s"

  :repl-options {:init-ns user}

  :profiles {:uberjar {:aot :all}

             :dev {:source-paths ["dev"]}})
