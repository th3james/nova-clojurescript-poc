(ns hello-nova.nova-interop
  (:require
    [cljs.core.async :as async :refer [>! <! chan close! go go-loop]]))


(defn register-command
  [name f]
  (js/nova.commands.register name f))


(defn run-process
  [editor executable & args]
  (let [msg-chan (chan)
        result-chan (chan 1)
        process (js/Process. "/usr/bin/env"  (clj->js {"cwd" editor.path
                                                      "args" (into-array (cons executable args))
                                                      "shell" true}))]

    (.onStdout process (fn [line] (go (>! msg-chan [:out line]))))
    (.onStderr process (fn [line] (go (>! msg-chan [:err line]))))
    (.onDidExit process (fn [status] (go (>! msg-chan [:exit status]))))

    (.start process)

    (go-loop [data {:out [] :err []}]
      (let [[type msg] (<! msg-chan)]
        (cond
          (= type :out) (recur (update data :out conj msg))
          (= type :err) (recur (update data :err conj msg))
          (= type :exit) (do (>! result-chan (assoc data :exit msg))
                             (close! msg-chan)))))
    result-chan))
