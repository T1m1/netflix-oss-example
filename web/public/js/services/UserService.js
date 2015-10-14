angular.module('UserService', []).factory('User', ['$http', function($http) {
	
	return {
        // call to get all users
        getAllUser : function() {
            return $http.get('http://p-raclettus:1111/apiv1/user-service/users');
        },
		
		 // call to get one user
        getUser : function(userId) {
            return $http.get('http://p-raclettus:1111/apiv1/user-service/users/' + userId);
        },


        // these will work when more API routes are defined on the Node side of things
        // call to POST and create a new nerd
        create : function(nerdData) {
            return $http.post('/api/nerds', nerdData);
        },

        // call to DELETE a nerd
        delete : function(id) {
            return $http.delete('/api/nerds/' + id);
        }
    }       
	

}]);