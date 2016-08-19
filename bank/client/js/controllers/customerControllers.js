(function(window, document, undefined) {
  bankApp.controller('CustomerListCtrl', ['$scope', '$location', 'utils',
    'customerSrv',
    function(
      $scope, $location, utils, customerSrv) {
      $scope.customers = [];
      customerSrv.getListCustomers()
        .then(handleSuccess, utils.handleServerError);

      function handleSuccess(response) {
        $scope.customers = response.data;
      };

      $scope.details = function(customerId) {
        $location.path('/customers/' + customerId);
      };

      $scope.createCustomer = function() {
        $location.path('/customers/create');
      };
    }
  ]);

  bankApp.controller('CreateCustomerCtrl', ['$scope', '$location', 'utils',
    'customerSrv',
    function($scope, $location, utils, customerSrv) {
      $scope.customer = {};

      $scope.create = function(valid) {
        if (valid) {
          customerSrv.createCustomer($scope.customer)
            .then(handleSuccess, utils.handleServerError);
        }
      };

      function handleSuccess(response) {
        $scope.customer = {};
        alert("Customer created!");
        $location.path("/customers");
      };
    }
  ]);

  bankApp.controller('CustomerDetailsCtrl', ['$scope', '$routeParams',
    '$location', '$timeout', 'utils', 'customerSrv',
    function($scope, $routeParams, $location, $timeout, utils,
      customerSrv) {
      init();

      function init() {
        $scope.customer = {};
        $scope.originalValues = [];
        customerSrv.getCustomer($routeParams.customerId)
          .then(handleSuccessGetCustomer, utils.handleServerError);
        customerSrv.getCustomerAccounts($routeParams.customerId)
          .then(handleSuccessGetCustomerAccounts, utils.handleServerError);
        customerSrv.getCustomerLoans($routeParams.customerId)
          .then(handleSuccessGetCustomerLoans, utils.handleServerError);
      };

      function handleSuccessGetCustomer(response) {
        $timeout(function() {
          $scope.customer = response.data;
        });
      };

      function handleSuccessGetCustomerAccounts(response) {
        $timeout(function() {
          $scope.customer.accounts = response.data;
        }, 100);
      };

      function handleSuccessGetCustomerLoans(response) {
        $timeout(function() {
          $scope.customer.loans = response.data;
        }, 200);
      };

      $scope.hasAccounts = function() {
        return $scope.customer.accounts !== undefined && $scope.customer
          .accounts.length > 0;
      };

      $scope.hasLoans = function() {
        return $scope.customer.loans !== undefined && $scope.customer
          .loans.length > 0;
      };

      $scope.isEditable = function(property) {
        return $scope.originalValues[property] !== undefined;
      };

      $scope.setEditable = function(property) {
        $scope.originalValues[property] = $scope.customer[property];
      };

      $scope.saveEdit = function(valid, property) {
        if (valid) {
          var update = {};
          update[property] = $scope.customer[property];
          customerSrv.updateCustomer(update, $scope.customer.id)
            .then(handleSuccessUpdateCustomer(property), utils.handleServerError);
        };
      };

      $scope.cancelEdit = function(property) {
        $scope.customer[property] = $scope.originalValues[property];
        $scope.originalValues[property] = undefined;
      };

      $scope.deleteCustomer = function() {
        var del = confirm("Do you really want to delete this user?");
        if (del) {
          customerSrv.deleteCustomer($scope.customer.id)
            .then(handleSuccessDeleteCustomer, utils.handleServerError);
        };
      };

      function handleSuccessUpdateCustomer(property) {
        return function(response) {
          $scope.originalValues[property] = undefined;
          $timeout(function() {
            $scope.customer[property] = response.data[property];
          });
        };
      };

      function handleSuccessDeleteCustomer(response) {
        $scope.customer = {};
        $location.path("/customers/list");
      };

      $scope.addAccount = function() {
        $location.path("/customers/" + $routeParams.customerId +
          "/accounts/add");
      };

      $scope.addLoan = function() {
        $location.path("/customers/" + $routeParams.customerId +
          "/loans/add");
      };

      $scope.accountRemove = function(accountId) {
        customerSrv.removeAccount($routeParams.customerId, accountId)
          .then(handleSuccessRemoveAccount, utils.handleServerError);
      };

      function handleSuccessRemoveAccount(response) {
        customerSrv.getCustomerAccounts($routeParams.customerId)
          .then(handleSuccessGetCustomerAccounts, utils.handleServerError);
      };

      $scope.loamRemove = function(loanId) {
        customerSrv.removeLoan($routeParams.customerId, loanId)
          .then(handleSuccessRemoveLoan, utils.handleServerError);
      };

      function handleSuccessRemoveLoan(response) {
        customerSrv.getCustomerLoans($routeParams.customerId)
          .then(handleSuccessGetCustomerLoans, utils.handleServerError);
      };
    }
  ]);

  bankApp.controller('AddCustomerAccountCtrl', ['$scope', '$location',
    '$routeParams', '$timeout', 'utils', 'customerSrv',
    'accountSrv',
    function($scope, $location, $routeParams, $timeout, utils,
      customerSrv, accountSrv) {
      init();

      function init() {
        $scope.account = {};
        $scope.accountList = [];
        this.customerAccounts = [];
        customerSrv.getCustomerAccounts($routeParams.customerId)
          .then(handleSuccessGetCustomerAccounts, utils.handleServerError);
        accountSrv.getListAccounts()
          .then(handleSuccessGetListAccounts, utils.handleServerError);
      };

      $scope.addExistingAccount = function(valid) {
        if (valid) {
          customerSrv.addExistingAccount({ 'id': $scope.account.id },
              $routeParams.customerId)
            .then(handleSuccessAddAccount, utils.handleServerError);
        }
      };

      function handleSuccessAddAccount(response) {
        $scope.account = {};
        alert('Account added!');
        $location.path("/customers/" + $routeParams.customerId);
      };

      function handleSuccessGetCustomerAccounts(response) {
        this.customerAccounts = response.data;
      };

      function isOwner(account) {
        for (var i = this.customerAccounts.length - 1; i >=
          0; i--) {
          if (this.customerAccounts[i].id === account.id)
            return false;
        };
        return true;
      };

      function handleSuccessGetListAccounts(response) {
        $timeout(function() {
          $scope.accountList = response.data.filter(isOwner);
        }, 100);
      };
    }
  ]);

  bankApp.controller('AddCustomerLoanCtrl', ['$scope', '$location',
    '$routeParams', '$timeout', 'utils', 'customerSrv',
    'loanSrv',
    function($scope, $location, $routeParams, $timeout, utils,
      customerSrv, loanSrv) {
      init();

      function init() {
        $scope.loan = {};
        $scope.loanList = [];
        this.customerLoans = [];
        customerSrv.getCustomerLoans($routeParams.customerId)
          .then(handleSuccessGetCustomerLoans, utils.handleServerError);
        loanSrv.getListLoans()
          .then(handleSuccessGetListLoans, utils.handleServerError);
      };

      $scope.addExistingLoan = function(valid) {
        if (valid) {
          customerSrv.addExistingLoan({ 'id': $scope.loan.id },
              $routeParams.customerId)
            .then(handleSuccessAddLoan, utils.handleServerError);
        }
      };

      function handleSuccessAddLoan(response) {
        $scope.loan = {};
        alert('Loan added!');
        $location.path("/customers/" + $routeParams.customerId);
      };

      function handleSuccessGetCustomerLoans(response) {
        this.customerLoans = response.data;
      };

      function isOwner(loan) {
        for (var i = this.customerLoans.length - 1; i >=
          0; i--) {
          if (this.customerLoans[i].id === loan.id)
            return false;
        };
        return true;
      };

      function handleSuccessGetListLoans(response) {
        $timeout(function() {
          $scope.loanList = response.data.filter(isOwner);
        }, 100);
      };
    }
  ]);
})(window, document);