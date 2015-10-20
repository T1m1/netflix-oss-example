angular.module('MailboxService', []).factory('Mailbox', ['$http', '$rootScope', function ($http, $rootScope) {

    return {
        getInboxOf: function (userId) {
            return $http.get('http://' + $rootScope.host + ':1111/apiv1/mailbox-service/inbox/' + userId);
        }
    }

}]);