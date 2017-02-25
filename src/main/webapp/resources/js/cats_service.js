'use strict';

angular.module('myApp')
    .constant('sockJsProtocols', [])
    .factory('StompClient', ['sockJsProtocols', '$q', function (sockJsProtocols, $q) {
        var stompClient;
        var wrappedSocket = {
            init: function (url) {
                if (sockJsProtocols.length > 0) {
                    stompClient = Stomp.over(new SockJS(url, null, {transports: sockJsProtocols}));
                }
                else {
                    stompClient = Stomp.over(new SockJS(url));
                }
            },
            connect: function () {
                return $q(function (resolve, reject) {
                    if (!stompClient) {
                        reject("STOMP client not created");
                    } else {
                        stompClient.connect({}, function (frame) {
                            resolve(frame);
                        }, function (error) {
                            reject("STOMP protocol error " + error);
                        });
                    }
                });
            },
            disconnect: function () {
                stompClient.disconnect();
            },
            subscribe: function (destination) {
                var deferred = $q.defer();
                if (!stompClient) {
                    deferred.reject("STOMP client not created");
                } else {
                    stompClient.subscribe(destination, function (message) {
                        deferred.notify(JSON.parse(message.body));
                    });
                }
                return deferred.promise;
            },
            send: function (destination, headers, object) {
                stompClient.send(destination, headers, object);
            }
        };
        return wrappedSocket;
    }])
    .factory('CatService', ['StompClient', '$http', '$q', function (stompClient, $http, $q) {

        var REST_SERVICE_URI = 'http://localhost:8080/cats/';

        var factory = {
            fetchAllCats: fetchAllCats,
            fetchCat: fetchCat,
            createCat: createCat,
            updateCat: updateCat,
            deleteCat: deleteCat,
            likeCat: likeCat,
            connect: connect,
            disconnect: disconnect,
            fetchMessage: fetchMessage
        };

        return factory;

        function disconnect() {
            stompClient.disconnect();
        }

        function connect(url) {
            stompClient.init(url);
            return stompClient.connect().then(function (frame) {
                return frame.headers['user-name'];
            });
        }

        function fetchMessage(userId) {
            return stompClient.subscribe('/topic/message/' + userId);
        }

        function fetchAllCats() {
            var deferred = $q.defer();
            $http.get(REST_SERVICE_URI)
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    },
                    function (errResponse) {
                        console.error('Error while fetching cats');
                        deferred.reject(errResponse);
                    }
                );
            return deferred.promise;
        }

        function createCat(cat) {
            var deferred = $q.defer();
            $http.post(REST_SERVICE_URI, cat)
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    },
                    function (errResponse) {
                        console.error('Error while creating cat');
                        deferred.reject(errResponse);
                    }
                );
            return deferred.promise;
        }


        function updateCat(cat, id) {
            var deferred = $q.defer();
            $http.put(REST_SERVICE_URI + id, cat)
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    },
                    function (errResponse) {
                        console.error('Error while updating cat');
                        deferred.reject(errResponse);
                    }
                );
            return deferred.promise;
        }

        function deleteCat(id) {
            var deferred = $q.defer();
            $http.delete(REST_SERVICE_URI + id)
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    },
                    function (errResponse) {
                        console.error('Error while deleting cat');
                        deferred.reject(errResponse);
                    }
                );
            return deferred.promise;
        }

        function fetchCat(id) {
            var deferred = $q.defer();
            $http.get(REST_SERVICE_URI + id)
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    },
                    function (errResponse) {
                        console.error('Error while fetching cat');
                        deferred.reject(errResponse);
                    }
                );
            return deferred.promise;
        }

        function likeCat(id) {
            var deferred = $q.defer();
            $http.put(REST_SERVICE_URI + "liked/" + id)
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    },
                    function (errResponse) {
                        console.error('Error while liked cat');
                        deferred.reject(errResponse);
                    }
                );
            return deferred.promise;
        }
    }]);
