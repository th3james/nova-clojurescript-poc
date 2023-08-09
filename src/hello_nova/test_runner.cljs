(ns hello-nova.test-runner
  (:require
    [cljs.test :refer [run-tests]]
    [hello-nova.commands-test]))


(defn ^:export main
  []
  (let [results (run-tests 'hello-nova.commands-test)]
    (if (-> results :fail + :error)
      (js/process.exit 1)
      (js/process.exit 0))))
