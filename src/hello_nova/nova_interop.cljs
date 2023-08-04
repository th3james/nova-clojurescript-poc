(ns hello-nova.nova-interop)

(defn register-command
  [name f]
  (js/nova.commands.register name f))
