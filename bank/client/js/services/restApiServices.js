(function(window, document, undefined) {
  var serverDomain = "http://localhost:9192";

  bankApp.service('accountSrv', ['$http', function($http) {
    this.getListAccounts = function() {
      return $http.get(serverDomain + "/accounts");
    };

    this.createAccount = function (account) {
      return $http.post(serverDomain+"/accounts", account);
    };
  }]);

  bankApp.service('branchSrv', ['$http', 'utils', function($http, utils) {
    this.getListBranches = function() {
      return $http.get(serverDomain + "/branches");
    };
    this.createBranch = function (branch) {
      return $http.post(serverDomain+"/branchs", branch);
    };
  }]);

  bankApp.service('customerSrv', ['$http', 'utils', function($http, utils) {
    this.getListCustomers = function() {
      return $http.get(serverDomain + "/customers");
    };

    this.createCustomer = function (customer) {
      return $http.post(serverDomain+"/customers", customer);
    };
  }]);

  bankApp.service('loansSrv', ['$http', 'utils', function($http, utils) {
    this.getListLoans = function() {
      return $http.get(serverDomain + "/loans");
    };
    this.createLoan = function (loan) {
      return $http.post(serverDomain+"/loans", loan);
    };
  }]);
})(window, document);