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

      $scope.accountDetails = function(accountId) {
        $location.path("/customers/" + $routeParams.customerId +
          "/accounts/" + accountId);
      };

      $scope.loanDetails = function(loanId) {
        $location.path("/customers/" + $routeParams.customerId +
          "/loans/" + loanId);
      };

      $scope.addAccount = function() {
        $location.path("/customers/" + $routeParams.customerId +
          "/accounts/add");
      };
    }
  ]);

  bankApp.controller('CustomerAccountCtrl', ['$scope', '$routeParams',
    '$location', '$timeout', 'utils', 'customerSrv', 'accountSrv',
    function($scope, $routeParams, $location, $timeout, utils,
      customerSrv, accountSrv) {
      init();

      function init() {
        $scope.account = {};
        $scope.editable = {
          deposit: false,
          withdraw: false,
          amount: undefined
        };
        customerSrv.getCustomerAccount($routeParams.customerId,
            $routeParams.accountId)
          .then(handleSuccessGetCustomerAccount, utils.handleServerError);
        accountSrv.getAccountBranch($routeParams.accountId)
          .then(handleSuccessGetAccountBranch, utils.handleServerError);
        accountSrv.getAccountOwners($routeParams.accountId)
          .then(handleSuccessGetAccountOwners, utils.handleServerError);
      };

      function handleSuccessGetCustomerAccount(response) {
        $timeout(function() {
          $scope.account = response.data;
        });
      };

      function handleSuccessGetAccountBranch(response) {
        $timeout(function() {
          $scope.account.branch = response.data;
        }, 100);
      };

      function handleSuccessGetAccountOwners(response) {
        $timeout(function() {
          $scope.account.owners = response.data;
        }, 200);
      };

      $scope.hasOwners = function() {
        return $scope.account.owners !== undefined && $scope.account.owners
          .length > 0;
      };

      $scope.remove = function() {
        customerSrv.removeAccount($routeParams.customerId, $routeParams
            .accountId)
          .then(handleSuccessRemoveAccount, utils.handleServerError);
      };

      function handleSuccessRemoveAccount(response) {
        $scope.account = {};
        $location.path("/customers/" + $routeParams.customerId);
      };

      $scope.isEditable = function(property) {
        return $scope.editable[property];
      };

      $scope.setEditable = function(property) {
        resetEditable();
        $scope.editable[property] = true;
      };

      function resetEditable() {
        $scope.editable = {
          deposit: false,
          withdraw: false,
          amount: undefined
        };
      };

      $scope.saveEdit = function(valid, property) {
        if (valid) {
          var amount = { amount: $scope.editable.amount };
          if (property === 'deposit') {
            customerSrv.depositInAccount(amount,
                $routeParams.customerId, $routeParams.accountId)
              .then(handleSuccessDeposit, utils.handleServerError);
          };
          if (property === 'withdraw') {
            customerSrv.withdrawFromAccount(amount,
                $routeParams.customerId, $routeParams.accountId)
              .then(handleSuccessWithdraw, utils.handleServerError);
          };
        };
      };

      $scope.cancelEdit = function(property) {
        resetEditable();
      };

      function handleSuccessDeposit(response) {
        resetEditable();
        $timeout(function() {
          $scope.account.balance = response.data.balance;
        });
      };

      function handleSuccessWithdraw(response) {
        resetEditable();
        $timeout(function() {
          $scope.account.balance = response.data.balance;
        });
      };
    }
  ]);

  bankApp.controller('AddCustomerAccountCtrl', ['$scope', '$location',
    '$routeParams', '$timeout', 'utils', 'customerSrv', 'branchSrv',
    'accountSrv',
    function($scope, $location, $routeParams, $timeout, utils,
      customerSrv,
      branchSrv, accountSrv) {
      init();

      function init() {
        $scope.accountType = "existing";
        $scope.account = {};
        $scope.accountList = [];
        this.customerAccounts = [];
        $scope.branches = [];
        branchSrv.getListBranches()
          .then(handleSuccessBranchList, utils.handleServerError);
        customerSrv.getCustomerAccounts($routeParams.customerId)
          .then(handleSuccessGetCustomerAccounts, utils.handleServerError);
        accountSrv.getListAccounts()
          .then(handleSuccessGetListAccounts, utils.handleServerError);
      };

      $scope.addNewAccount = function(valid) {
        if (valid) {
          customerSrv.addNewAccount($scope.account, $routeParams.customerId)
            .then(handleSuccessAddAccount, utils.handleServerError);
        }
      };

      $scope.addExistingAccount = function(valid) {
        if (valid) {
          customerSrv.addExistingAccount({ 'id': $scope.account.id },
              $routeParams.customerId)
            .then(handleSuccessAddAccount, utils.handleServerError);
        }
      };

      $scope.isSelectedAccountType = function(accountType) {
        return $scope.accountType === accountType;
      };

      $scope.resetAccount = function() {
        $scope.account = {};
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

      function handleSuccessBranchList(response) {
        $timeout(function() {
          $scope.branches = response.data;
        }, 200);
      };
    }
  ]);

  bankApp.controller('CustomerLoanCtrl', ['$scope', '$routeParams',
    '$location', '$timeout', 'utils', 'customerSrv', 'loanSrv',
    function($scope, $routeParams, $location, $timeout, utils,
      customerSrv, loanSrv) {
      init();

      function init() {
        $scope.loan = {};
        $scope.editable = {
          deposit: false,
          amount: undefined
        };
        customerSrv.getCustomerLoan($routeParams.customerId,
            $routeParams.loanId)
          .then(handleSuccessGetCustomerLoan, utils.handleServerError);
        loanSrv.getLoanBranch($routeParams.loanId)
          .then(handleSuccessGetLoanBranch, utils.handleServerError);
        loanSrv.getLoanOwners($routeParams.loanId)
          .then(handleSuccessGetLoanOwners, utils.handleServerError);
      };

      function handleSuccessGetCustomerLoan(response) {
        $timeout(function() {
          $scope.loan = response.data;
        });
      };

      function handleSuccessGetLoanBranch(response) {
        $timeout(function() {
          $scope.loan.branch = response.data;
        }, 100);
      };

      function handleSuccessGetLoanOwners(response) {
        $timeout(function() {
          $scope.loan.owners = response.data;
        }, 200);
      };

      $scope.hasOwners = function() {
        return $scope.loan.owners !== undefined && $scope.loan.owners
          .length > 0;
      };

      $scope.remove = function() {
        customerSrv.removeLoan($routeParams.customerId, $routeParams
            .loanId)
          .then(handleSuccessRemoveLoan, utils.handleServerError);
      };

      function handleSuccessRemoveLoan(response) {
        $scope.loan = {};
        $location.path("/customers/" + $routeParams.customerId +
          "/loans/" + $routeParams.loanId);
      };

      $scope.isEditable = function(property) {
        return $scope.editable[property];
      };

      $scope.setEditable = function(property) {
        resetEditable();
        $scope.editable[property] = true;
      };

      function resetEditable() {
        $scope.editable = {
          deposit: false,
          withdraw: false,
          amount: undefined
        };
      };

      $scope.saveEdit = function(valid, property) {
        if (valid) {
          var amount = { amount: $scope.editable.amount };
          if (property === 'deposit') {
            customerSrv.depositInLoan(amount,
                $routeParams.customerId, $routeParams.loanId)
              .then(handleSuccessDeposit, utils.handleServerError);
          };
        };
      };

      $scope.cancelEdit = function(property) {
        resetEditable();
      };

      function handleSuccessDeposit(response) {
        resetEditable();
        $timeout(function() {
          $scope.loan.amount = response.data.amount;
        });
      };
    }
  ]);
})(window, document);