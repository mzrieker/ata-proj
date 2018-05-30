(ns ata-proj.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [ata-proj.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[ata-proj started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[ata-proj has shut down successfully]=-"))
   :middleware wrap-dev})
