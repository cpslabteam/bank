var bankApp = angular.module('bankApp', ['ngRoute']);
(function(window, document, undefined) {
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
        .when('/loans', {
          templateUrl: 'html/loan-list.html',
          controller: 'LoanListCtrl'
        })
        .when('/branches', {
          templateUrl: 'html/branch-list.html',
          controller: 'BranchListCtrl'
        })
        .
      otherwise({
        redirectTo: '/customers'
      });
    }
  ]);
})(window, document);