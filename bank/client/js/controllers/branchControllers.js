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
})(window, document);