angular.module('appRoutes', []).config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {

    $routeProvider

        // home page
        .when('/', {
            templateUrl: 'views/home.html',
            controller: 'MainController'
        })

        .when('/user', {
            templateUrl: 'views/user.html',
            controller: 'UserController'
        })

        .when('/mailbox', {
            templateUrl: 'views/mailbox.html',
            controller: 'MailboxController'
        })
        .
        when('/message', {
            templateUrl: 'views/message.html',
            controller: 'MessageController'
        });

    $locationProvider.html5Mode(true);

}]).run(function ($http, $rootScope) {
    $rootScope.host = 'localhost';

    $http.get('/hostname').success(function (host) {
        $rootScope.host = host.host;
    });

});