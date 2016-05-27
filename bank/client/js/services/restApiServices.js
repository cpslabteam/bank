(function(window, document, undefined) {
  var serverDomain = "http://localhost:9192";

  bankApp.service('accountSrv', ['$http', function($http) {
    this.getListAccounts = function() {
      return $http.get(serverDomain + "/accounts");
    };

    this.createAccount = function(account) {
      return $http.post(serverDomain + "/accounts", account);
    };

    this.getAccount = function(id) {
      return $http.get(serverDomain + "/accounts/" + id);
    };

    this.updateAccount = function(update, id) {
      return $http.put(serverDomain + "/accounts/" + id, update);
    };

    this.updateAccountBranch = function(update, id) {
      return $http.put(serverDomain + "/accounts/" + id + "/branch",
        update);
    };

    this.deleteAccount = function(id) {
      return $http.delete(serverDomain + "/accounts/" + id);
    };

    this.getAccountOwners = function(id) {
      return $http.get(serverDomain + "/accounts/" + id + "/owners");
    };

    this.getAccountBranch = function(id) {
      return $http.get(serverDomain + "/accounts/" + id + "/branch");
    };
  }]);

  bankApp.service('branchSrv', ['$http', 'utils', function($http, utils) {
    this.getListBranches = function() {
      return $http.get(serverDomain + "/branches");
    };

    this.createBranch = function(branch) {
      return $http.post(serverDomain + "/branches", branch);
    };

    this.getBranch = function(id) {
      return $http.get(serverDomain + "/branches/" + id);
    };

    this.updateBranch = function(update, id) {
      return $http.put(serverDomain + "/branches/" + id, update)
    };

    this.deleteBranch = function(id) {
      return $http.delete(serverDomain + "/branches/" + id);
    };

    this.getBranchAccounts = function(id) {
      return $http.get(serverDomain + "/branches/" + id + "/accounts");
    };

    this.getBranchLoans = function(id) {
      return $http.get(serverDomain + "/branches/" + id + "/loans");
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

    this.deleteCustomer = function(id) {
      return $http.delete(serverDomain + "/customers/" + id);
    };

    this.getCustomerAccounts = function(id) {
      return $http.get(serverDomain + "/customers/" + id +
        "/accounts");
    };

    this.getCustomerLoans = function(id) {
      return $http.get(serverDomain + "/customers/" + id + "/loans");
    };

    this.getCustomerAccount = function(customerId, accountId) {
      return $http.get(serverDomain + "/customers/" + customerId +
        "/accounts/" + accountId);
    };

    this.getCustomerLoan = function(customerId, loanId) {
      return $http.get(serverDomain + "/customers/" + customerId +
        "/loans/" + loanId);
    };

    this.depositInAccount = function(amount, customerId, accountId) {
      return $http.post(serverDomain + "/customers/" + customerId +
        "/accounts/" + accountId + "/deposit", amount);
    };

    this.withdrawFromAccount = function(amount, customerId, accountId) {
      return $http.post(serverDomain + "/customers/" + customerId +
        "/accounts/" + accountId + "/withdraw", amount);
    };

    this.depositInLoan = function(amount, customerId, loanId) {
      return $http.post(serverDomain + "/customers/" + customerId +
        "/loans/" + loanId + "/deposit", amount);
    };

    this.removeAccount = function(customerId, accountId) {
      return $http.delete(serverDomain + "/customers/" + customerId +
        "/accounts/" + accountId);
    };

    this.removeLoan = function(customerId, loanId) {
      return $http.delete(serverDomain + "/customers/" + customerId +
        "/loans/" + loanId);
    };
  }]);

  bankApp.service('loanSrv', ['$http', 'utils', function($http, utils) {
    this.getListLoans = function() {
      return $http.get(serverDomain + "/loans");
    };

    this.createLoan = function(loan) {
      return $http.post(serverDomain + "/loans", loan);
    };

    this.getLoan = function(id) {
      return $http.get(serverDomain + "/loans/" + id);
    };

    this.updateLoan = function(update, id) {
      return $http.put(serverDomain + "/loans/" + id, update);
    };

    this.updateLoanBranch = function(update, id) {
      return $http.put(serverDomain + "/loans/" + id + "/branch",
        update);
    };

    this.deleteLoan = function(id) {
      return $http.delete(serverDomain + "/loans/" + id);
    };

    this.getLoanOwners = function(id) {
      return $http.get(serverDomain + "/loans/" + id + "/owners");
    };

    this.getLoanBranch = function(id) {
      return $http.get(serverDomain + "/loans/" + id + "/branch");
    };
  }]);
})(window, document);