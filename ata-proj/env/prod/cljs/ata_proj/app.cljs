(ns ata-proj.app
  (:require [ata-proj.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
