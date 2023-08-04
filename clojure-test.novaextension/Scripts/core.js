var shadow$provide = {};
(function(root, factory) {
  if (typeof define === "function" && define.amd) {
    define([], factory);
  } else if (typeof module === "object" && module.exports) {
    module.exports = factory();
  } else {
    root.returnExports = factory();
  }
})(this, function() {
  var shadow$umd$export = null;
'use strict';/*

 Copyright The Closure Library Authors.
 SPDX-License-Identifier: Apache-2.0
*/
function a(){return console.log("get-branch called")};shadow$umd$export=function(){console.log("Running core ClojureScript code.");return nova.commands.register("clojure-test.getBranch",a)};return shadow$umd$export;
});