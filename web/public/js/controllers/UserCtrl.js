angular.module('UserCtrl', []).controller('UserController', function($scope, User) {

	User.getAllUser().then(function test(response){
		console.log(response);
		$scope.user = response.data;
	}), function errorCallback(err) {
		console.log(err);
	};
});