(ns hello-nova.test-runner
  (:require
    [cljs.test :refer [run-tests]]
    [hello-nova.commands-test]))


(defn ^:export main
  []
  (enable-console-print!))
