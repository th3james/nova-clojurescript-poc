(ns hello-nova.commands
  (:require
    [cljs.core.async :as async :refer [<! go]]
    [cljs.pprint :as pprint]
    [hello-nova.dependencies :refer [nova-impl]]
    [hello-nova.nova-interop :refer [register-command run-process show-notification]]))


(defn get-branch
  ([editor]
   (get-branch editor (fn [editor executable & args] (run-process @nova-impl editor executable args))))
  ([editor run-process-fn]
   (go
     (let [result (<! (run-process-fn editor "git", "rev-parse", "--abbrev-ref", "HEAD"))]
       ; TODO inspect :exit, handle errors
       (:out result)))))


(defn open-on-github
  ([editor] (open-on-github editor get-branch (fn [message] (show-notification @nova-impl message))))
  ([editor get-branch-fn show-notification-fn]
   (go
     (let [branch (<! (get-branch-fn editor))]
       (show-notification-fn branch)))))
