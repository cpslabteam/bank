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
})(window, document);