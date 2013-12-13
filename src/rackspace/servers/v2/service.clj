(ns rackspace.servers.v2.service
  (:require [clojure.data.json :as json]
            [clj-http.client :as http]
            [rackspace.const :as const]
            [rackspace.identity :as identity]
            [rackspace.services :as services]))

(defn get-new-server-payload [server-name image-id flavor-id]
  {:body (json/write-str {:server
                          {:name server-name
                           :imageRef image-id
                           :flavorRef flavor-id}})
   :content-type :json})

(defn create-server [identity-response region server-name image-id flavor-id]
  (let [base-url (services/get-cloud-servers-region-url identity-response region)]
    (http/post
      (str base-url const/server-path)
      {:content-type :json
       :headers {const/x-auth-token (identity/get-token identity-response)}})))

(defn get-server-list [identity-response region]
  (let [base-url (services/get-cloud-servers-region-url identity-response region)]
    (http/get
      (str base-url const/server-detail-path)
      {:accept :json
       :headers {const/x-auth-token (identity/get-token identity-response)}})))