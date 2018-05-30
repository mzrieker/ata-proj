(ns user
  (:require [ata-proj.config :refer [env]]
            [clojure.spec.alpha :as s]
            [expound.alpha :as expound]
            [mount.core :as mount]
            [ata-proj.figwheel :refer [start-fw stop-fw cljs]]
            [ata-proj.core :refer [start-app]]
            [ata-proj.db.core]
            [conman.core :as conman]
            [luminus-migrations.core :as migrations]))

(alter-var-root #'s/*explain-out* (constantly expound/printer))

(defn start []
  (mount/start-without #'ata-proj.core/repl-server))

(defn stop []
  (mount/stop-except #'ata-proj.core/repl-server))

(defn restart []
  (stop)
  (start))

(defn restart-db []
  (mount/stop #'ata-proj.db.core/*db*)
  (mount/start #'ata-proj.db.core/*db*)
  (binding [*ns* 'ata-proj.db.core]
    (conman/bind-connection ata-proj.db.core/*db* "sql/queries.sql")))

(defn reset-db []
  (migrations/migrate ["reset"] (select-keys env [:database-url])))

(defn migrate []
  (migrations/migrate ["migrate"] (select-keys env [:database-url])))

(defn rollback []
  (migrations/migrate ["rollback"] (select-keys env [:database-url])))

(defn create-migration [name]
  (migrations/create name (select-keys env [:database-url])))


