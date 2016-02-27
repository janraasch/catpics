(ns catpics.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [catpics.core-test]))

(doo-tests 'catpics.core-test)

