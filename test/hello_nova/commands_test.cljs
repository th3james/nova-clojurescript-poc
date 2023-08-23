(ns hello-nova.commands-test
  (:require
    [cljs.core.async :as async :refer [<! go chan put!]]
    [cljs.test :refer-macros [deftest is testing async run-tests]]
    [hello-nova.commands :as commands]))


(deftest get-branch
  (testing "calls git and returns the branch name"
    (async done
           (let [mock-process-channel (chan 1)
                 mock-git-response "some-mock-value"
                 mock-run-process (fn [editor & _]
                                    (is (= editor :stub-editor))
                                    (put! mock-process-channel {:out mock-git-response})
                                    mock-process-channel)]
             (go (let [received-value (<! (commands/get-branch :stub-editor mock-run-process))]
                   (is (= received-value mock-git-response))
                   (done)))))))


(deftest open-on-github
  (testing "gets the current branch and opens the GitHub URL for it"
    (async done
           (let [mock-channel (chan 1)
                 mock-branch-name "mock-branch-name"
                 done-channel (chan 1)]

             (commands/open-on-github :editor-mock
                                      ;; Mock for get-branch
                                      (fn [editor]
                                        (is (= editor :editor-mock))
                                        (put! mock-channel mock-branch-name)
                                        mock-channel)
                                      ;; Mock for show-notification
                                      (fn [arg]
                                        (put! done-channel arg)))

             ;; Wait for the notification call to finish
             (go (let [received-value (<! done-channel)]
                   (is (= received-value mock-branch-name))
                   (done)))))))


(run-tests)
