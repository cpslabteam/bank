(function(window, document, undefined) {
  var serverDomain = "http://localhost:9192";

  bankApp.service('accountSrv', ['$http', function($http) {
    this.getListAccounts = function() {
      return $http.get(serverDomain + "/accounts");
    };

    this.createAccount = function(account) {
      return $http.post(serverDomain + "/accounts", account);
    };
  }]);

  bankApp.service('branchSrv', ['$http', 'utils', function($http, utils) {
    this.getListBranches = function() {
      return $http.get(serverDomain + "/branches");
    };
    this.createBranch = function(branch) {
      return $http.post(serverDomain + "/branches", branch);
    };
  }]);

  bankApp.service('customerSrv', ['$http', 'utils', function($http, utils) {
    this.getListCustomers = function() {
      return $http.get(serverDomain + "/customers");
    };

    this.createCustomer = function(customer) {
      return $http.post(serverDomain + "/customers", customer);
    };

    this.getCustomer = function(id) {
      return $http.get(serverDomain + "/customers/" + id);
    };

    this.updateCustomer = function(update, id) {
      return $http.put(serverDomain + "/customers/" + id, update)
    };

    this.deleteCustomer = function (id) {
      return $http.delete(serverDomain + "/customers/" + id);
    };

    this.getCustomerAccounts = function (id) {
      return $http.get(serverDomain + "/customers/" + id + "/accounts");
    };

    this.getCustomerLoans = function (id) {
      return $http.get(serverDomain + "/customers/" + id + "/loans");
    };
  }]);

  bankApp.service('loanSrv', ['$http', 'utils', function($http, utils) {
    this.getListLoans = function() {
      return $http.get(serverDomain + "/loans");
    };
    this.createLoan = function(loan) {
      return $http.post(serverDomain + "/loans", loan);
    };
  }]);
})(window, document);