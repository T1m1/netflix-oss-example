angular.module('MessageCtrl', []).controller('MessageController', function ($scope, Message) {

    $scope.sendMessage = function (message) {

        Message.sendMessage(message).then(function success(response) {
            $scope.messages = response.data;
            console.log('success');
        }).catch(function errorCallback(err) {
            console.log(err);
        });
    }

});