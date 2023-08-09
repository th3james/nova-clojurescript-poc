(ns hello-nova.commands-test
  (:require
    [cljs.core.async :as async :refer [<! go chan put!]]
    [cljs.test :refer-macros [deftest is testing]]
    [hello-nova.commands :as commands]
    [hello-nova.nova-interop :as nova]))


(defn mock-run-process
  [editor & args]
  (let [c (chan)]
    (go (put! c "master"))
    c))


(def done-chan (chan))


(defn mock-show-notification
  [message]
  (is (= message "not master"))
  (put! done-chan :done))


(deftest get-branch-test
  (testing "get-branch behavior"
    (with-redefs [nova/run-process mock-run-process
                  nova/show-notification mock-show-notification]
      (go
        (commands/get-branch nil)
        (is (= :done (<! done-chan)))))))
