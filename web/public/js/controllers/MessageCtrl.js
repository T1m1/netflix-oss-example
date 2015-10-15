angular.module('MessageCtrl', []).controller('MessageController', function ($scope, Message) {

    $scope.sendMessage = function () {
        var msg = {};
        msg.fromId =  $scope.fromUser.userId;
        msg.toId = $scope.toUser.userId;
        msg.subject = $scope.subject;
        msg.message = $scope.message;
        console.log(msg);
        Message.sendMessage(msg).then(function success(response) {
            console.log(response)
        }).catch(function errorCallback(err) {
            console.log(err);
        });
    };

    $scope.selectFromUser = function (fromUser) {
        $scope.fromUser = fromUser;
    };

    $scope.selectToUser = function (toUser) {
        $scope.toUser = toUser;
    };

});