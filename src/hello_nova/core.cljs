(ns hello-nova.core)


(defn handle-input
  [result]
  (when result
    (.openURL js/nova result (fn [success] nil))))


(defn open-url
  []
  (let [options (clj->js {"placeholder" "https://foobar.com"
                          "prompt" "Open"})]
    (js/nova.workspace.showInputPanel
      "Enter the URL to open:"
      options
      handle-input)))


(defn main
  []
  (.register (aget js/nova "commands")
             "clojure-test.openURL"
             (fn [_]
               (open-url))))
