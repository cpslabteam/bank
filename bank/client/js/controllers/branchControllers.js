(function(window, document, undefined) {
  bankApp.controller('BranchListCtrl', ['$scope', '$location', 'utils',
    'branchSrv',
    function(
      $scope, $location, utils, branchSrv) {
      $scope.branches = [];
      branchSrv.getListBranches()
        .then(handleSuccess, utils.handleServerError);

      function handleSuccess(response) {
        $scope.branches = response.data;
      };

      $scope.createBranch = function() {
        $location.path("/branches/create");
      };

      $scope.details = function(branchId) {
        $location.path('/branches/' + branchId);
      };
    }
  ]);

  bankApp.controller('CreateBranchCtrl', ['$scope', '$location', 'utils',
    'branchSrv',
    function($scope, $location, utils, branchSrv) {
      $scope.branch = {};

      $scope.create = function(valid) {
        if (valid) {
          branchSrv.createBranch($scope.branch)
            .then(handleSuccess, utils.handleServerError);
        }
      };

      function handleSuccess(response) {
        $scope.branch = {};
        alert("Branch created!");
        $location.path("/branches/list");
      };
    }
  ]);

  bankApp.controller('BranchDetailsCtrl', ['$scope', '$routeParams',
    '$location', '$timeout', 'utils', 'branchSrv',
    function($scope, $routeParams, $location, $timeout, utils,
      branchSrv) {
      init();

      function init() {
        $scope.branch = {};
        $scope.originalValues = [];
        branchSrv.getBranch($routeParams.branchId)
          .then(handleSuccessGetBranch, utils.handleServerError);
        branchSrv.getBranchAccounts($routeParams.branchId)
          .then(handleSuccessGetBranchAccounts, utils.handleServerError);
        branchSrv.getBranchLoans($routeParams.branchId)
          .then(handleSuccessGetBranchLoans, utils.handleServerError);
      };

      function handleSuccessGetBranch(response) {
        $timeout(function() {
          $scope.branch = response.data;
        });
      };

      function handleSuccessGetBranchAccounts(response) {
        $timeout(function() {
          $scope.branch.accounts = response.data;
        }, 100);
      };

      function handleSuccessGetBranchLoans(response) {
        $timeout(function() {
          $scope.branch.loans = response.data;
        }, 200);
      };

      $scope.hasAccounts = function() {
        return $scope.branch.accounts !== undefined && $scope.branch
          .accounts.length > 0;
      };

      $scope.hasLoans = function() {
        return $scope.branch.loans !== undefined && $scope.branch
          .loans.length > 0;
      };

      $scope.isEditable = function(property) {
        return $scope.originalValues[property] !== undefined;
      };

      $scope.setEditable = function(property) {
        $scope.originalValues[property] = $scope.branch[property];
      };

      $scope.saveEdit = function(valid, property) {
        if (valid) {
          var update = {};
          update[property] = $scope.branch[property];
          branchSrv.updateBranch(update, $scope.branch.id)
            .then(handleSuccessUpdateBranch(property), utils.handleServerError);
        };
      };

      $scope.cancelEdit = function(property) {
        $scope.branch[property] = $scope.originalValues[property];
        $scope.originalValues[property] = undefined;
      };

      $scope.deleteBranch = function() {
        var del = confirm("Do you really want to delete this user?");
        if (del) {
          branchSrv.deleteBranch($scope.branch.id)
            .then(handleSuccessDeleteBranch, utils.handleServerError);
        };
      };

      function handleSuccessUpdateBranch(property) {
        return function(response) {
          $scope.originalValues[property] = undefined;
          $timeout(function() {
            $scope.branch[property] = response.data[property];
          });
        };
      };

      function handleSuccessDeleteBranch(response) {
        $scope.branch = {};
        $location.path("/branchs/list");
      };

      $scope.accountDetails = function(accountId) {
        $location.path("/branches/" + $routeParams.branchId +
          "/accounts/" + accountId);
      };

      $scope.addAccount = function() {
        $location.path("/branches/" + $routeParams.branchId +
          "/accounts/add");
      };

      $scope.loanDetails = function(loanId) {
        $location.path("/branches/" + $routeParams.branchId +
          "/loans/" + loanId);
      };
    }
  ]);

  bankApp.controller('BranchAccountCtrl', ['$scope', '$routeParams',
    '$location', '$timeout', 'utils', 'accountSrv', 'branchSrv',
    function($scope, $routeParams, $location, $timeout, utils, accountSrv,
      branchSrv) {
      init();

      function init() {
        $scope.account = {};
        $scope.originalValues = [];
        branchSrv.getBranchAccount($routeParams.branchId, $routeParams.accountId)
          .then(handleSuccessGetBranchAccount, utils.handleServerError);
        accountSrv.getAccountOwners($routeParams.accountId)
          .then(handleSuccessGetAccountOwners, utils.handleServerError);
      };

      function handleSuccessGetBranchAccount(response) {
        $timeout(function() {
          $scope.account = response.data;
        });
      };

      function handleSuccessGetAccountOwners(response) {
        $timeout(function() {
          $scope.account.owners = response.data;
        }, 100);
      };

      $scope.hasOwners = function() {
        return $scope.account.owners !== undefined && $scope.account
          .owners.length > 0;
      };

      $scope.isEditable = function(property) {
        return $scope.originalValues[property] !== undefined;
      };

      $scope.setEditable = function(property) {
        $scope.originalValues[property] = $scope.account[property];
      };

      $scope.saveEdit = function(valid, property) {
        if (valid) {
          var update = {};
          update[property] = $scope.account[property];
          branchSrv.updateBranchAccount(update, $routeParams.branchId,
              $routeParams.accountId)
            .then(handleSuccessUpdateBranchAccount(property), utils.handleServerError);
        };
      };

      $scope.cancelEdit = function(property) {
        $scope.account[property] = $scope.originalValues[property];
        $scope.originalValues[property] = undefined;
      };

      $scope.deleteAccount = function() {
        var del = confirm("Do you really want to delete this account?");
        if (del) {
          branchSrv.deleteBranchAccount($routeParams.branchId,
              $routeParams.accountId)
            .then(handleSuccessDeleteBranchAccount, utils.handleServerError);
        };
      };

      function handleSuccessUpdateBranchAccount(property) {
        return function(response) {
          $scope.originalValues[property] = undefined;
          $timeout(function() {
            $scope.account[property] = response.data[property];
          });
        };
      };

      function handleSuccessDeleteBranchAccount(response) {
        $scope.account = {};
        $location.path("/branches/list");
      };
    }
  ]);

  bankApp.controller('AddBranchAccountCtrl', ['$scope', '$location',
    '$routeParams', '$timeout', 'utils', 'branchSrv',
    'accountSrv',
    function($scope, $location, $routeParams, $timeout, utils,
      branchSrv, accountSrv) {
      init();

      function init() {
        $scope.account = {};
      };

      $scope.addNewAccount = function(valid) {
        if (valid) {
          branchSrv.addNewAccount($scope.account, $routeParams.branchId)
            .then(handleSuccessAddAccount, utils.handleServerError);
        }
      };

      function handleSuccessAddAccount(response) {
        $scope.account = {};
        alert('Account added!');
        $location.path("/branches/" + $routeParams.branchId);
      };
    }
  ]);

  bankApp.controller('BranchLoanCtrl', ['$scope', '$routeParams',
    '$location', '$timeout', 'utils', 'loanSrv', 'branchSrv',
    function($scope, $routeParams, $location, $timeout, utils, loanSrv,
      branchSrv) {
      init();

      function init() {
        $scope.loan = {};
        $scope.originalValues = [];
        branchSrv.getBranchLoan($routeParams.branchId, $routeParams.loanId)
          .then(handleSuccessGetBranchLoan, utils.handleServerError);
        loanSrv.getLoanOwners($routeParams.loanId)
          .then(handleSuccessGetLoanOwners, utils.handleServerError);
      };

      function handleSuccessGetBranchLoan(response) {
        $timeout(function() {
          $scope.loan = response.data;
        });
      };

      function handleSuccessGetLoanOwners(response) {
        $timeout(function() {
          $scope.loan.owners = response.data;
        }, 100);
      };

      $scope.hasOwners = function() {
        return $scope.loan.owners !== undefined && $scope.loan
          .owners.length > 0;
      };

      $scope.isEditable = function(property) {
        return $scope.originalValues[property] !== undefined;
      };

      $scope.setEditable = function(property) {
        $scope.originalValues[property] = $scope.loan[property];
      };

      $scope.saveEdit = function(valid, property) {
        if (valid) {
          var update = {};
          update[property] = $scope.loan[property];
          branchSrv.updateBranchLoan(update, $routeParams.branchId,
              $routeParams.loanId)
            .then(handleSuccessUpdateBranchLoan(property), utils.handleServerError);
        };
      };

      $scope.cancelEdit = function(property) {
        $scope.loan[property] = $scope.originalValues[property];
        $scope.originalValues[property] = undefined;
      };

      $scope.deleteLoan = function() {
        var del = confirm("Do you really want to delete this loan?");
        if (del) {
          branchSrv.deleteBranchLoan($routeParams.branchId,
              $routeParams.loanId)
            .then(handleSuccessDeleteBranchLoan, utils.handleServerError);
        };
      };

      function handleSuccessUpdateBranchLoan(property) {
        return function(response) {
          $scope.originalValues[property] = undefined;
          $timeout(function() {
            $scope.loan[property] = response.data[property];
          });
        };
      };

      function handleSuccessDeleteBranchLoan(response) {
        $scope.loan = {};
        $location.path("/branches/list");
      };
    }
  ]);
})(window, document);