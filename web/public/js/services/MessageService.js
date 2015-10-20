angular.module('MessageService', []).factory('Message', ['$http', '$rootScope', function ($http, $rootScope) {

    return {
        sendMessage: function (message) {
            return $http.post('http://' + $rootScope.host + ':1111/apiv1/message-service/messages', message);
        }
    }

}]);