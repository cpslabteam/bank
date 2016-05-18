(function(window, document, undefined) {
  bankApp.controller('BranchListCtrl', ['$scope', 'utils', 'branchSrv',
    function(
      $scope, utils, branchSrv) {
      $scope.branches = [];
      branchSrv.getListBranches()
        .then(handleSuccess, utils.handleServerError);

      function handleSuccess(response) {
        $scope.branches = response.data;
      }
    }
  ]);

  bankApp.controller('CreateBranchCtrl', ['$scope', '$location', 'utils',
    'branchSrv',
    function($scope, $location, utils, customerSrv) {
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
        $location.path("/branchs");
      };
    }
  ]);
})(window, document);