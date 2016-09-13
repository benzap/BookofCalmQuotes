(defproject bookofcalmquotes "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-oauth "1.4.1"]
                 [twitter-api "0.7.8"]
                 [org.postgresql/postgresql "9.4.1210.jre7"]
                 [org.clojure/java.jdbc "0.6.2-alpha3"]
                 [overtone/at-at "1.2.0"]
                 [com.taoensso/timbre "4.7.4"]
                 [environ "1.1.0"]]
  :plugins [[lein-environ "1.1.0"]]
  :main ^:skip-aot bookofcalmquotes.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
