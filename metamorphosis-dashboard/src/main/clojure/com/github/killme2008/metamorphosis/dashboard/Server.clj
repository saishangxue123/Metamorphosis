(ns com.github.killme2008.metamorphosis.dashboard.Server
  (:import [com.taobao.metamorphosis.server.utils MetaConfig])
  (:gen-class
   :methods [[start [com.taobao.metamorphosis.server.assembly.MetaMorphosisBroker] org.eclipse.jetty.server.Server]])
  (:use [ring.adapter.jetty]
        [environ.core])
  (:require [clojure.tools.logging :as logging])
  (:use [com.github.killme2008.metamorphosis.dashboard.handler :only [app broker-ref]]))

(defn -start [this broker]
  (reset! broker-ref broker)
  (let [max-threads (Integer/valueOf (env :web-jetty-threads "30"))
        port (-> broker (.getMetaConfig) (.getDashboardHttpPort))]
    (logging/info "Starting dashboard http server at port " port)
    (let [server (run-jetty app {:port port
                                 :max-threads max-threads
                                 :join? false})]
      (logging/info "Started dashboard http server successfully.")
      server)))


