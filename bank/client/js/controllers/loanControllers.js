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

  bankApp.controller('CreateLoanCtrl', ['$scope', '$location', 'utils',
    'loanSrv',
    function($scope, $location, utils, customerSrv) {
      $scope.loan = {};

      $scope.create = function(valid) {
        if (valid) {
          loanSrv.createLoan($scope.loan)
            .then(handleSuccess, utils.handleServerError);
        }
      };

      function handleSuccess(response) {
        $scope.loan = {};
        alert("Loan created!");
        $location.path("/loans");
      };
    }
  ]);
})(window, document);