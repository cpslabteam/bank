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
        .when('/customers/:customerId/accounts/:accountId', {
          templateUrl: 'html/customer-account.html',
          controller: 'CustomerAccountCtrl'
        })
        .when('/customers/:customerId/loans/add', {
          templateUrl: 'html/add-customer-loan.html',
          controller: 'AddCustomerLoanCtrl'
        })
        .when('/customers/:customerId/loans/:loanId', {
          templateUrl: 'html/customer-loan.html',
          controller: 'CustomerLoanCtrl'
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
        .when('/accounts/:accountId/owners/:ownerId', {
          templateUrl: 'html/account-owner.html',
          controller: 'AccountOwnerCtrl'
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
        .when('/loans/:loanId/owners/:ownerId', {
          templateUrl: 'html/loan-owner.html',
          controller: 'LoanOwnerCtrl'
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
        .when('/branches/:branchId/accounts/:accountId', {
          templateUrl: 'html/branch-account.html',
          controller: 'BranchAccountCtrl'
        })
        .when('/branches/:branchId/loans/:loanId', {
          templateUrl: 'html/branch-loan.html',
          controller: 'BranchLoanCtrl'
        })
        .otherwise({
          redirectTo: '/customers/list'
        });
    }
  ]);
})(window, document);