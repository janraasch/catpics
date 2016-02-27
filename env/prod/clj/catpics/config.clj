(ns catpics.config
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[catpics started successfully]=-"))
   :middleware identity})
