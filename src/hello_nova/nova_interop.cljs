(ns hello-nova.nova-interop
  (:require
    [cljs.core.async :as async :refer [>! <! chan close! go go-loop]]))


(defprotocol INova

  (register-command [_ name f])

  (run-process [_ editor executable args])

  (show-notification [_ message]))


(defrecord Nova
  []

  INova

  (register-command
    [_ name f]
    (js/nova.commands.register name f))


  (run-process
    [_ editor executable args]
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


  (show-notification
    [_ message]
    (js/console.log message)))


(defrecord NovaStub
  []

  INova

  (register-command
    [_ name f]
    (print "Registering command" name))


  (run-process
    [_ editor executable args]
    (print "Running process" executable args))


  (show-notification
    [_ message]
    (print "Showing notification" message)))
