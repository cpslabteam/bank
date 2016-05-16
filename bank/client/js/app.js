var bankApp = angular.module('bankApp', ['ngRoute', 'bankControllers']);

bankApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
    when('/customers', {
        templateUrl: 'html/customer-list.html',
        controller: 'CustomerListCtrl'
      })
      .when('/accounts', {
        templateUrl: 'html/account-list.html',
        controller: 'AccountListCtrl'
      })
      .
    otherwise({
      redirectTo: '/customers'
    });
  }
]);