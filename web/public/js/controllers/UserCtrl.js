angular.module('UserCtrl', []).controller('UserController', function($scope, User) {

	User.getAllUser().then(function test(response){
		console.log(response);
		$scope.user = response.data;
	}), function errorCallback(err) {
		console.log(err);
	};
	
	$scope.createUser = function(val) {
		debugger;
		User.createUser(val).then(function(response){
			console.log('user created');
		$('#myModal').modal('toggle')

		}), function errorCallback(err) {
			console.log(err);
		};
	}, function errorCallback(err) {
		console.log(err);
	};
});