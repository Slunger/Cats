'use strict';

var App = angular.module('myApp', []);

App.factory('httpRequestInterceptor', function () {
    return {
        request: function (config) {
            var metas = window.document.getElementsByName("_csrf");
            config.headers['X-CSRF-TOKEN'] = metas[0].value;
            return config;
        }
    };
});

App.config(function ($httpProvider) {
    $httpProvider.interceptors.push('httpRequestInterceptor');
});