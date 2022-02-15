(defproject competition "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/java.jdbc "0.3.0-alpha5"]
                 [mysql/mysql-connector-java "5.1.6"]
                 [ring "1.8.1"]
                 [ring-basic-authentication "1.1.0"]
                 [compojure "1.6.1"]
                 [clj-http "3.10.1"]
                 [hiccup "1.0.5"]
                 [ring/ring-defaults "0.3.2"]
                 [markdown-clj "1.10.5"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler competition.core/app}
  :main ^:skip-aot competition.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]
                       [javax.servlet/servlet-api "2.5"]
                       [ring/ring-mock "0.4.0"]}})
