(ns hello-nova.commands
  (:require
    [cljs.core.async :as async :refer [<! go]]
    [hello-nova.nova-interop :as nova]))


(defn get-branch
  ([editor] (get-branch editor nova/run-process nova/show-notification))
  ([editor run-process-fn show-notification-fn]
   (go
     (let [branch (<! (run-process-fn editor "git", "rev-parse", "--abbrev-ref", "HEAD"))]
       (show-notification-fn branch)))))
