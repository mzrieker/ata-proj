(ns ata-proj.routes.home
  (:require [ata-proj.layout :as layout]
            [ata-proj.db.core :as db]
            [ring.util.response :refer [redirect]]
            [struct.core :as st]
            [compojure.core :refer [defroutes GET]]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.core :refer [defroutes GET DELETE]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]))

(defn home-page [{:keys [flash]}]
  (layout/render
    "home.html"
    (merge {:messages (db/get-messages)}
           (select-keys flash [:name :message :errors]))))

(def message-schema
  [[:name
    st/required
    st/string]

   [:message
    st/required
    st/string
    {:message "message must contain at least 10 characters"
     :validate #(> (count %) 9)}]])

(defn validate-message [params]
  (first (st/validate params message-schema)))

(defn save-message! [{:keys [params]}]
  (if-let [errors (validate-message params)]
    (-> (response/found "/")
        (assoc :flash (assoc params :errors errors)))
    (do
      (db/save-message!
       (assoc params :timestamp (java.util.Date.)))
      (response/found "/"))))

(defn delete-message! [{:keys [params]}]
  (do
      (db/delete-message!
       (assoc params :timestamp (java.util.Date.)))
      (response/found "/")))


(defroutes home-routes
  (GET "/" request (home-page request))
  (POST "/" request (save-message! request))
  (DELETE "/" request (delete-message! request))
  (GET "/docs" []
       (-> (response/ok (-> "docs/docs.md" io/resource slurp))
           (response/header "Content-Type" "text/plain; charset=utf-8"))))
