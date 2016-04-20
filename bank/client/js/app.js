var bankApp = angular.module('bankApp', ['ngRoute', 'bankControllers']);

bankApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
    when('/customers', {
        templateUrl: 'html/customer-list.html',
        controller: 'CustomerListCtrl'
      })
      .
    otherwise({
      redirectTo: '/customers'
    });
  }
]);