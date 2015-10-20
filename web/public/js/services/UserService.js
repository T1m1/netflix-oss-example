angular.module('UserService', []).factory('User', ['$http', '$rootScope', function ($http, $rootScope) {

    return {
        // call to get all users
        getAllUser: function () {
            return $http.get('http://' + $rootScope.host + ':1111/apiv1/user-service/users');
        },

        // call to get one user
        getUser: function (userId) {
            return $http.get('http://' + $rootScope.host + ':1111/apiv1/user-service/users/' + userId);
        },

        // these will work when more API routes are defined on the Node side of things
        // call to POST and create a new nerd
        createUser: function (user) {
            return $http.post('http://' + $rootScope.host + ':1111/apiv1/user-service/users', user);
        }
    }


}]);