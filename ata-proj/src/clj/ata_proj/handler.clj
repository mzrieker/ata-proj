(ns ata-proj.handler
  (:require 
            [ata-proj.layout :refer [error-page]]
            [ata-proj.routes.home :refer [home-routes]]
            [ata-proj.routes.services :refer [service-routes]]
            [compojure.core :refer [routes wrap-routes]]
            [compojure.route :as route]
            [ata-proj.env :refer [defaults]]
            [mount.core :as mount]
            [ata-proj.middleware :as middleware]))

(mount/defstate init-app
  :start ((or (:init defaults) identity))
  :stop  ((or (:stop defaults) identity)))

(mount/defstate app
  :start
  (middleware/wrap-base
    (routes
      (-> #'home-routes
          (wrap-routes middleware/wrap-csrf)
          (wrap-routes middleware/wrap-formats))
          #'service-routes
          (route/not-found
             (:body
               (error-page {:status 404
                            :title "page not found"}))))))

