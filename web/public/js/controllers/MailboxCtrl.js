angular.module('MailboxCtrl', []).controller('MailboxController', function ($scope, Mailbox, User) {

    User.getAllUser().then(function test(response) {
        console.log(response);
        $scope.user = response.data;
    }).catch(function errorCallback(err) {
        console.log(err);
    });


    $scope.selectUser = function (user) {
        $scope.selected = user.firstName + ' ' + user.lastName;

        Mailbox.getInboxOf(user.userId).then(function success(response) {
            $scope.messages = response.data;

        }).catch(function errorCallback(err) {
            console.log(err);
        });
    }

});