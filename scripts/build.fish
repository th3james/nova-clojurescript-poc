#!/usr/bin/env fish

clj -M --main cljs.main --output-to clojure-test.novaextension/Scripts/main.js --target none --compile hello-nova.core 
