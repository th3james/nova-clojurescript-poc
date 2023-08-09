(ns hello-nova.commands
  (:require
    [cljs.core.async :as async :refer [<! go]]
    [hello-nova.nova-interop :as nova]))


(defn get-branch
  [editor]
  (go
    (let [branch (<! (nova/run-process editor "git", "rev-parse", "--abbrev-ref", "HEAD"))]
      (nova/show-notification branch))))
