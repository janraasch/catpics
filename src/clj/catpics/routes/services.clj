(ns catpics.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [clj-http.client :as client]
            [clojure.java.io :as io]
            [clojure.xml :as xml]))
            
(defn parse-xml [xml]
  (-> xml .getBytes io/input-stream xml/parse))
      
(defn get-first-child [tag xml-node]
  (->> xml-node :content (filter #(= (:tag %) tag)) first))
  
(defn parse-link [link]
  (->> link (get-first-child :url) :content first))
  
(defn parse-links [links]
  (->> links
       (get-first-child :data)
       (get-first-child :images)
       :content
       (map parse-link)))

(defn get-links [link-count]
 (-> "http://thecatapi.com/api/images/get?format=xml&results_per_page="
     (str link-count)
     client/get
     :body
     parse-xml
     parse-links))

(defapi service-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Sample API"
                           :description "Sample Services"}}}}
  (context "/api" []
    :tags ["for the cats"]

    (GET "/cat-links" []
      :query-params [link-count :- Long]
      :summary "returns a collection of image links"
      :return [s/Str]
      (ok (get-links link-count)))))
