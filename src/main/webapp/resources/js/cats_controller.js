'use strict';

angular.module('myApp').controller('CatController', ['$scope', 'CatService', function ($scope, CatService) {
    var self = this;
    self.cat = {id: null, age: null, color: '', breed: '', name: '', weight: null};
    self.cats = [];
    self.submit = submit;
    self.edit = edit;
    self.remove = remove;
    self.reset = reset;
    self.like = like;
    self.notifications = [];
    self.fetchMessage = fetchMessage;

    fetchAllCats();

    function fetchMessage(userId) {
        CatService.connect('/notification')
            .then(function () {
                CatService.fetchMessage(userId).then(null, null,
                    function (msg) {
                        if (self.notifications.length === 2) {
                            self.notifications.pop();
                        }
                        self.notifications.unshift(msg.message);
                    }
                );
            });
    }

    function fetchAllCats() {
        CatService.fetchAllCats()
            .then(
                function (res) {
                    self.cats = res;
                },
                function (errResponse) {
                    console.error('Error while fetching cats');
                }
            );
    }

    function fetchCat(id) {
        CatService.fetchCat(id)
            .then(
                function (res) {
                    self.cat = res;
                },
                function (errResponse) {
                    console.error('Error while fetching cat');
                }
            );
    }

    function createCat(cat) {
        CatService.createCat(cat)
            .then(
                fetchAllCats,
                function (errResponse) {
                    console.error('Error while creating cat');
                }
            );
    }

    function updateCat(cat, id) {
        CatService.updateCat(cat, id)
            .then(
                fetchAllCats,
                function (errResponse) {
                    console.error('Error while updating cat');
                }
            );
    }

    function deleteCat(id) {
        CatService.deleteCat(id)
            .then(
                fetchAllCats,
                function (errResponse) {
                    console.error('Error while deleting cat');
                }
            );
    }

    function submit() {
        if (self.cat.id === null) {
            console.log('Saving New Cat', self.cat);
            createCat(self.cat);
        } else {
            updateCat(self.cat, self.cat.id);
            console.log('Cat updated with id ', self.cat.id);
        }
        reset();
    }

    function edit(id) {
        console.log('id to be edited', id);
        for (var i = 0; i < self.cats.length; i++) {
            if (self.cats[i].id === id) {
                self.cat = angular.copy(self.cats[i]);
                break;
            }
        }
    }

    function remove(id) {
        console.log('id to be deleted', id);
        if (self.cat.id === id) {//clean form if the user to be deleted is shown there.
            reset();
        }
        deleteCat(id);
    }

    function like(id) {
        CatService.likeCat(id)
            .then(
                fetchAllCats,
                function (errResponse) {
                    console.error('Error while liked cat');
                }
            );
    }

    function reset() {
        self.cat = {id: null, age: null, color: '', breed: '', name: '', weight: null};
        $scope.myForm.$setPristine(); //reset Form
    }

}]);
