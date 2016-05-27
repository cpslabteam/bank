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
    }
  ]);
})(window, document);