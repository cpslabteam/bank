(function(window, document, undefined) {
  bankApp.controller('AccountListCtrl', ['$scope', '$location', 'utils', 'accountSrv',
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

      $scope.createAccount = function () {
        $location.path("/accounts/create");
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