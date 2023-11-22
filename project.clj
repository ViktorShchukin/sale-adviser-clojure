(defproject sale-adviser-clojure "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring/ring-core "1.11.0-RC1"]
                 [ring/ring-jetty-adapter "1.11.0-RC1"]
                 [compojure "1.7.0"]
                 [ch.qos.logback/logback-classic "1.4.11"]]
  :main ^:skip-aot sale-adviser-clojure.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
