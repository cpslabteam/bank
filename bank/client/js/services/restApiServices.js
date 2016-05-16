(function(window, document, undefined) {
  var serverDomain = "http://localhost:9192";

  bankApp.service('accountSrv', ['$http', function($http) {
    this.getListAccounts = function() {
      return $http.get(serverDomain + "/accounts");
    };
  }]);

  bankApp.service('branchSrv', ['$http', 'utils', function($http, utils) {
    this.getListBranches = function() {
      return $http.get(serverDomain + "/branches");
    };
  }]);

  bankApp.service('customerSrv', ['$http', 'utils', function($http, utils) {
    this.getListCustomers = function() {
      return $http.get(serverDomain + "/customers");
    };
  }]);

  bankApp.service('loansSrv', ['$http', 'utils', function($http, utils) {
    this.getListLoans = function() {
      return $http.get(serverDomain + "/loans");
    };
  }]);
})(window, document);