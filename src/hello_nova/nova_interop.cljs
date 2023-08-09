(ns hello-nova.nova-interop
  (:require
    [cljs.core.async :as async :refer [>! chan close! go]]))


(defn register-command
  [name f]
  (js/nova.commands.register name f))


(defn run-process
  [editor executable & args]
  (let [chan (chan)
        process (js/Process. "usr/bin/env"  (clj->js {"cwd" editor.path
                                                      "args" (into-array (cons executable args))
                                                      "shell" true}))]
    (.onStdout process (fn [line] (go (>! chan line))))
    (.onStderr process (fn [line] (go (>! chan line))))
    (.onDidExit process (fn [status]
                          (go (>! chan status)
                          (close! chan))))
    (.start process)
    chan))
