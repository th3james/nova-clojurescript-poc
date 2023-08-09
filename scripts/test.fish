#!/usr/bin/env fish

npx shadow-cljs compile test
node out/test.js
