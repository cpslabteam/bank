(function(window, document, undefined) {
  bankApp.controller('LoanListCtrl', ['$scope', '$location', 'utils',
    'loanSrv',
    function(
      $scope, $location, utils, loanSrv) {
      $scope.loans = [];
      loanSrv.getListLoans()
        .then(handleSuccess, utils.handleServerError);

      function handleSuccess(response) {
        $scope.loans = response.data;
      };

      $scope.createLoan = function() {
        $location.path("/loans/create");
      };
    }
  ]);

  bankApp.controller('CreateLoanCtrl', ['$scope', '$location', 'utils',
    'loanSrv', 'branchSrv',
    function($scope, $location, utils, loanSrv, branchSrv) {
      init();

      function init() {
        $scope.loan = {};
        $scope.branches = [];
        branchSrv.getListBranches()
          .then(handleSuccessBranchList, utils.handleServerError);
      };

      $scope.create = function(valid) {
        if (valid) {
          loanSrv.createLoan($scope.loan)
            .then(handleSuccessCreate, utils.handleServerError);
        }
      };

      function handleSuccessCreate(response) {
        $scope.loan = {};
        alert("Loan created!");
        $location.path("/loans/list");
      };

      function handleSuccessBranchList(response) {
        $scope.branches = response.data;
      };
    }
  ]);
})(window, document);