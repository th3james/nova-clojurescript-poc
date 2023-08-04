(ns hello-nova.core
  (:require [hello-nova.nova-interop :as nova]))


(defn get-branch
  []
  (js/console.log "get-branch called"))


(defn main
  []
  (js/console.log "Running core ClojureScript code.")
  (nova/register-command "clojure-test.getBranch" get-branch))


(defn repl-entry
  []
  (print "Running ClojureScript REPL."))
