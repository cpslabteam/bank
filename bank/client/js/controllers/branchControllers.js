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

  bankApp.controller('CreateBranchCtrl', ['$scope', '$location', '$timeout',
    'utils',
    'branchSrv', 'divisionSrv',
    function($scope, $location, $timeout, utils, branchSrv, divisionSrv) {
      init();

      function init() {
        $scope.branch = {};
        $scope.divisions = [];
        divisionSrv.getListDivisions()
          .then(handleSuccessGetListDivisions, utils.handleServerError);
      }

      function handleSuccessGetListDivisions(response) {
        $timeout(function() {
          $scope.divisions = response.data;
        }, 100);
      };

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
    '$location', '$timeout', 'utils', 'branchSrv', 'divisionSrv',
    function($scope, $routeParams, $location, $timeout, utils,
      branchSrv, divisionSrv) {
      init();

      function init() {
        $scope.branch = {};
        $scope.originalValues = [];
        branchSrv.getBranch($routeParams.branchId)
          .then(handleSuccessGetBranch, utils.handleServerError);
        branchSrv.getBranchDivision($routeParams.branchId)
          .then(handleSuccessGetBranchDivision, utils.handleServerError);
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

      function handleSuccessGetBranchDivision(response) {
        $timeout(function() {
          $scope.branch.division = response.data;
        }, 100);
      };

      function handleSuccessGetBranchAccounts(response) {
        $timeout(function() {
          $scope.branch.accounts = response.data;
        }, 200);
      };

      function handleSuccessGetBranchLoans(response) {
        $timeout(function() {
          $scope.branch.loans = response.data;
        }, 300);
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
        if (property === 'division') {
          $scope.originalValues[property] = $scope.branch.division;
          divisionSrv.getListDivisions()
            .then(handleSuccessGetDivisionList, utils.handleServerError);
        } else
          $scope.originalValues[property] = $scope.branch[property];
      };

      $scope.saveEdit = function(valid, property) {
        if (valid) {
          var update = {};
          if (property === 'division') {
            update['id'] = $scope.branch.division.id;
            branchSrv.updateBranchDivision(update, $scope.branch.id)
              .then(handleSuccessUpdateBranchDivision, utils.handleServerError);
          } else {
            update[property] = $scope.branch[property];
            branchSrv.updateBranch(update, $scope.branch.id)
              .then(handleSuccessUpdateBranch(property), utils.handleServerError);
          };
        };
      };

      $scope.cancelEdit = function(property) {
        if (property === 'division') {
          $scope.branch.division = $scope.originalValues[property];
        } else
          $scope.branch[property] = $scope.originalValues[property];
        $scope.originalValues[property] = undefined;
      };

      $scope.deleteBranch = function() {
        var del = confirm("Do you really want to delete this branch?");
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

      function handleSuccessUpdateBranchDivision(response) {
        $scope.originalValues['division'] = undefined;
        $timeout(function() {
          $scope.branch.division = response.data;
        }, 100);
      };

      function handleSuccessGetDivisionList(response) {
        $scope.divisions = response.data;
      };
    }
  ]);
})(window, document);