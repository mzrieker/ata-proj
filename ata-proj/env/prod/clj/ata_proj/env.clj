(ns ata-proj.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[ata-proj started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[ata-proj has shut down successfully]=-"))
   :middleware identity})
