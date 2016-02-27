(ns catpics.config
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [catpics.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[catpics started successfully using the development profile]=-"))
   :middleware wrap-dev})
