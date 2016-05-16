(function(window, document, undefined) {
  bankApp.controller('LoanListCtrl', ['$scope', 'utils', 'loansSrv', function(
    $scope, utils, loansSrv) {
    $scope.loans = [];
    loansSrv.getListLoans()
      .then(handleSuccess, utils.handleServerError);

    function handleSuccess(response) {
      $scope.loans = response.data;
    }
  }]);
})(window, document);