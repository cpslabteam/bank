(function(window, document, undefined) {
  bankApp.controller('AccountListCtrl', ['$scope', 'utils', 'accountSrv',
    function(
      $scope, utils, accountSrv) {
      $scope.accounts = [];
      accountSrv.getListAccounts()
        .then(handleSuccess, utils.handleServerError);

      function handleSuccess(response) {
        $scope.accounts = response.data;
      }
    }
  ]);
})(window, document);