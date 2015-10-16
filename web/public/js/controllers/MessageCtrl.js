angular.module('MessageCtrl', []).controller('MessageController', function ($scope, Message) {

    $scope.sendMessage = function () {
        var msg = {
            fromId: $scope.fromUser.userId,
            toId: $scope.toUser.userId,
            subject: $scope.subject,
            message: $scope.message
        };
        console.log(msg);
        Message.sendMessage(msg).then(function success(response) {
            $scope.fromUser = null;
            $scope.toUser = null;
            $scope.subject = null;
            $scope.message = null;
            console.log(response)

            $scope.info = "Nachricht wurde erfolgreich gesendet!"
        }).catch(function errorCallback(err) {
            console.log(err);
            $scope.info = "Nachricht konnte nicht gesendet werden!"
        });
    };

    $scope.selectFromUser = function (fromUser) {
        $scope.fromUser = fromUser;
    };

    $scope.selectToUser = function (toUser) {
        $scope.toUser = toUser;
    };

});