(ns hello-nova.core
  (:require
    [hello-nova.commands :as commands]
    [hello-nova.dependencies :refer [nova-impl]]
    [hello-nova.nova-interop :refer [Nova NovaStub register-command]]))


(defn main
  []
  (js/console.log "Running core ClojureScript code.")
  (reset! nova-impl (Nova.))
  (register-command @nova-impl "clojure-test.open-on-github" commands/open-on-github))


(defn repl-entry
  []
  (print "Running ClojureScript REPL.")
  (reset! nova-impl (NovaStub.)))
