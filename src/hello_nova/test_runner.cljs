(ns hello-nova.test-runner
  (:require
    [hello-nova.commands-test]
    [hello-nova.dependencies :refer [nova-impl]]
    [hello-nova.nova-interop :refer [NovaStub]]))


(defn ^:export main
  []
  (print "Running ClojureScript tests.")
  (reset! nova-impl (NovaStub.))
  (enable-console-print!))
