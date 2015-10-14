angular.module('MailboxService', []).factory('Mailbox', ['$http', function($http) {

		return {
			// call to get all users
			getInboxOf : function(userId) {
				return $http.get('http://p-raclettus:1111/apiv1/mailbox-service/inbox/' + userId);
			}
    }       

}]);