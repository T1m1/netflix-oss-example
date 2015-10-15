angular.module('MessageService', []).factory('Message', ['$http', function ($http) {

    return {
        sendMessage: function (message) {
            return $http.post('http://p-raclettus:1111/apiv1/message-service/messages', message);
        }
    }

}]);