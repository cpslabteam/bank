var bankApp = angular.module('bankApp', ['ngRoute']);
(function(window, document, undefined) {
  bankApp.config(['$routeProvider',
    function($routeProvider) {
      $routeProvider.
      when('/customers/list', {
          templateUrl: 'html/customer-list.html',
          controller: 'CustomerListCtrl'
        })
        .when('/customers/create', {
          templateUrl: 'html/create-customer.html',
          controller: 'CreateCustomerCtrl'
        })
        .when('/accounts/list', {
          templateUrl: 'html/account-list.html',
          controller: 'AccountListCtrl'
        })
        .when('/accounts/create', {
          templateUrl: 'html/create-account.html',
          controller: 'CreateAccountCtrl'
        })
        .when('/loans/list', {
          templateUrl: 'html/loan-list.html',
          controller: 'LoanListCtrl'
        })
        .when('/branches/list', {
          templateUrl: 'html/branch-list.html',
          controller: 'BranchListCtrl'
        })
        .otherwise({
          redirectTo: '/customers/list'
        });
    }
  ]);
})(window, document);