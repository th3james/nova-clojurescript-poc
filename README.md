# Hello Nova from ClojureScript

PoC of writing a Nova extension using ClojureScript. The key challenge is [Nova extensions run in JavaScriptCore](https://docs.nova.app/extensions/#mainjs-and-javascript-entry-points). The current implementation solves this by setting the ClojureScript compiler target to `node-library`, then having a skinny `main.js` which `requires` and executes the compiled library.

## Commands

```sh
# Compile clojure to update the extension
./scripts/build.fish
# Start a watcher repl for use with Conjure
./scripts/start-repl.fish
```
