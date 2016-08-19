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
        .when('/customers/:customerId', {
          templateUrl: 'html/customer-details.html',
          controller: 'CustomerDetailsCtrl'
        })
        .when('/customers/:customerId/accounts/add', {
          templateUrl: 'html/add-customer-account.html',
          controller: 'AddCustomerAccountCtrl'
        })
        .when('/customers/:customerId/loans/add', {
          templateUrl: 'html/add-customer-loan.html',
          controller: 'AddCustomerLoanCtrl'
        })
        .when('/accounts/list', {
          templateUrl: 'html/account-list.html',
          controller: 'AccountListCtrl'
        })
        .when('/accounts/create', {
          templateUrl: 'html/create-account.html',
          controller: 'CreateAccountCtrl'
        })
        .when('/accounts/:accountId', {
          templateUrl: 'html/account-details.html',
          controller: 'AccountDetailsCtrl'
        })
        .when('/accounts/:accountId/owners/add', {
          templateUrl: 'html/add-account-owner.html',
          controller: 'AddAccountOwnerCtrl'
        })
        .when('/loans/list', {
          templateUrl: 'html/loan-list.html',
          controller: 'LoanListCtrl'
        })
        .when('/loans/create', {
          templateUrl: 'html/create-loan.html',
          controller: 'CreateLoanCtrl'
        })
        .when('/loans/:loanId', {
          templateUrl: 'html/loan-details.html',
          controller: 'LoanDetailsCtrl'
        })
        .when('/loans/:loanId/owners/add', {
          templateUrl: 'html/add-loan-owner.html',
          controller: 'AddLoanOwnerCtrl'
        })
        .when('/branches/list', {
          templateUrl: 'html/branch-list.html',
          controller: 'BranchListCtrl'
        })
        .when('/branches/create', {
          templateUrl: 'html/create-branch.html',
          controller: 'CreateBranchCtrl'
        })
        .when('/branches/:branchId', {
          templateUrl: 'html/branch-details.html',
          controller: 'BranchDetailsCtrl'
        })
        .otherwise({
          redirectTo: '/customers/list'
        });
    }
  ]);
})(window, document);