(ns hello-nova.core
  (:require
    [cljs.core.async :as async :refer [<! go]]
    [hello-nova.commands :as commands]
    [hello-nova.nova-interop :as nova]))


(defn main
  []
  (js/console.log "Running core ClojureScript code.")
  (nova/register-command "clojure-test.getBranch" commands/get-branch))


(defn repl-entry
  []
  (print "Running ClojureScript REPL."))
