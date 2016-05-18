(function(window, document, undefined) {
  bankApp.controller('AccountListCtrl', ['$scope', 'utils', 'accountSrv',
    function(
      $scope, utils, accountSrv) {
      init();

      function init() {
        $scope.accounts = [];
        accountSrv.getListAccounts()
          .then(handleSuccess, utils.handleServerError);
      };

      function handleSuccess(response) {
        $scope.accounts = response.data;
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
})(window, document);