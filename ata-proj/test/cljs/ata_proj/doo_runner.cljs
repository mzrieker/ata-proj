(ns ata-proj.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [ata-proj.core-test]))

(doo-tests 'ata-proj.core-test)

