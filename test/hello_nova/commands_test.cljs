(ns hello-nova.commands-test
  (:require
    [cljs.core.async :as async :refer [<! go chan put!]]
    [cljs.test :refer-macros [deftest is testing async run-tests]]
    [hello-nova.commands :as commands]
    [hello-nova.nova-interop :as nova]))


(deftest get-branch-test
  (testing "calls git and notifies the user"
    (async done
           (let [mock-channel (chan 1)
                 mock-value "some-mock-value"
                 done-channel (chan 1)]

             (commands/get-branch nil
                                  ;; Mock for run-process
                                  (fn [& _]
                                    (println "mock-run-process called")
                                    (put! mock-channel mock-value)
                                    mock-channel)
                                  ;; Mock for show-notification
                                  (fn [arg]
                                    (put! done-channel arg)))

             ;; Wait for the notification call to finish
             (go (let [received-value (<! done-channel)]
                   (is (= received-value mock-value))
                   (done)))))))


(run-tests)
