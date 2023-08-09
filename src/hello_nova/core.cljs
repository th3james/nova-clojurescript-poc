(ns hello-nova.core
  (:require
    [cljs.core.async :as async :refer [<! go]]
    [hello-nova.nova-interop :as nova]))


(defn get-branch
  [editor]
  (js/console.log "Getting branch.")
  (go
    (let [branch (<! (nova/run-process editor "git", "rev-parse", "--abbrev-ref", "HEAD"))]
      (js/console.log "Got branch:")
      (js/console.log branch))))


(defn main
  []
  (js/console.log "Running core ClojureScript code.")
  (nova/register-command "clojure-test.getBranch" get-branch))


(defn repl-entry
  []
  (print "Running ClojureScript REPL."))
