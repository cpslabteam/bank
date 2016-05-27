(function(window, document, undefined) {
  bankApp.controller('AccountListCtrl', ['$scope', '$location', 'utils',
    'accountSrv',
    function(
      $scope, $location, utils, accountSrv) {
      init();

      function init() {
        $scope.accounts = [];
        accountSrv.getListAccounts()
          .then(handleSuccess, utils.handleServerError);
      };

      function handleSuccess(response) {
        $scope.accounts = response.data;
      };

      $scope.createAccount = function() {
        $location.path("/accounts/create");
      };

      $scope.details = function(accountId) {
        $location.path('accounts/' + accountId);
      };
    }
  ]);

  bankApp.controller('CreateAccountCtrl', ['$scope', '$location', 'utils',
    'accountSrv', 'branchSrv',
    function($scope, $location, utils, accountSrv, branchSrv) {
      init();

      function init() {
        $scope.account = {};
        $scope.branches = [];
        branchSrv.getListBranches()
          .then(handleSuccessBranchList, utils.handleServerError);
      };

      $scope.create = function(valid) {
        if (valid) {
          accountSrv.createAccount($scope.account)
            .then(handleSuccessCreate, utils.handleServerError);
        }
      };

      function handleSuccessCreate(response) {
        $scope.account = {};
        alert("Account created!");
        $location.path("/accounts/list");
      };

      function handleSuccessBranchList(response) {
        $scope.branches = response.data;
      };
    }
  ]);

  bankApp.controller('AccountDetailsCtrl', ['$scope', '$routeParams',
    '$location', '$timeout', 'utils', 'accountSrv', 'branchSrv',
    function($scope, $routeParams, $location, $timeout, utils, accountSrv,
      branchSrv) {
      init();

      function init() {
        $scope.account = {};
        $scope.originalValues = [];
        accountSrv.getAccount($routeParams.accountId)
          .then(handleSuccessGetAccount, utils.handleServerError);
        accountSrv.getAccountBranch($routeParams.accountId)
          .then(handleSuccessGetAccountBranch, utils.handleServerError);
        accountSrv.getAccountOwners($routeParams.accountId)
          .then(handleSuccessGetAccountOwners, utils.handleServerError);
      };

      function handleSuccessGetAccount(response) {
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
        return $scope.account.owners !== undefined && $scope.account
          .owners.length > 0;
      };

      $scope.isEditable = function(property) {
        return $scope.originalValues[property] !== undefined;
      };

      $scope.setEditable = function(property) {
        if (property === 'branch') {
          $scope.originalValues[property] = $scope.account.branch;
          branchSrv.getListBranches()
            .then(handleSuccessGetBranchList, utils.handleServerError);
        } else
          $scope.originalValues[property] = $scope.account[property];
      };

      $scope.saveEdit = function(valid, property) {
        if (valid) {
          var update = {};
          if (property === 'branch') {
            update['id'] = $scope.account.branch.id;
            accountSrv.updateAccountBranch(update, $scope.account.id)
              .then(handleSuccessUpdateAccountBranch, utils.handleServerError);
          } else {
            update[property] = $scope.account[property];
            accountSrv.updateAccount(update, $scope.account.id)
              .then(handleSuccessUpdateAccount(property), utils.handleServerError);
          };
        };
      };

      $scope.cancelEdit = function(property) {
        if (property === 'branch') {
          $scope.account.branch = $scope.originalValues[property];
        } else {
          $scope.account[property] = $scope.originalValues[property];
        }
        $scope.originalValues[property] = undefined;
      };

      $scope.deleteAccount = function() {
        var del = confirm("Do you really want to delete this account?");
        if (del) {
          accountSrv.deleteAccount($scope.account.id)
            .then(handleSuccessDeleteAccount, utils.handleServerError);
        };
      };

      function handleSuccessUpdateAccount(property) {
        return function(response) {
          $scope.originalValues[property] = undefined;
          $timeout(function() {
            $scope.account[property] = response.data[property];
          });
        };
      };

      function handleSuccessUpdateAccountBranch(response) {
        $scope.originalValues['branch'] = undefined;
        $timeout(function() {
          $scope.account.branch = response.data;
        });
      };

      function handleSuccessDeleteAccount(response) {
        $scope.account = {};
        $location.path("/accounts/list");
      };

      function handleSuccessGetBranchList(response) {
        $scope.branches = response.data;
      };
    }
  ]);
})(window, document);