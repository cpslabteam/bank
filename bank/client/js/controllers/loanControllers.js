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

      $scope.details = function(loanId) {
        $location.path('/loans/' + loanId);
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

  bankApp.controller('LoanDetailsCtrl', ['$scope', '$routeParams',
    '$location', '$timeout', 'utils', 'loanSrv', 'branchSrv',
    function($scope, $routeParams, $location, $timeout, utils, loanSrv,
      branchSrv) {
      init();

      function init() {
        $scope.loan = {};
        $scope.originalValues = [];
        loanSrv.getLoan($routeParams.loanId)
          .then(handleSuccessGetLoan, utils.handleServerError);
        loanSrv.getLoanBranch($routeParams.loanId)
          .then(handleSuccessGetLoanBranch, utils.handleServerError);
        loanSrv.getLoanOwners($routeParams.loanId)
          .then(handleSuccessGetLoanOwners, utils.handleServerError);
      };

      function handleSuccessGetLoan(response) {
        $timeout(function() {
          $scope.loan = response.data;
        });
      };

      function handleSuccessGetLoanBranch(response) {
        $timeout(function() {
          $scope.loan.branch = response.data;
        }, 100);
      };

      function handleSuccessGetLoanOwners(response) {
        $timeout(function() {
          $scope.loan.owners = response.data;
        }, 200);
      };

      $scope.hasOwners = function() {
        return $scope.loan.owners !== undefined && $scope.loan
          .owners.length > 0;
      };

      $scope.isEditable = function(property) {
        return $scope.originalValues[property] !== undefined;
      };

      $scope.setEditable = function(property) {
        if (property === 'branch') {
          $scope.originalValues[property] = $scope.loan.branch;
          branchSrv.getListBranches()
            .then(handleSuccessGetBranchList, utils.handleServerError);
        } else
          $scope.originalValues[property] = $scope.loan[property];
      };

      $scope.saveEdit = function(valid, property) {
        if (valid) {
          var update = {};
          if (property === 'branch') {
            update['id'] = $scope.loan.branch.id;
            loanSrv.updateLoanBranch(update, $scope.loan.id)
              .then(handleSuccessUpdateLoanBranch, utils.handleServerError);
          } else {
            update[property] = $scope.loan[property];
            loanSrv.updateLoan(update, $scope.loan.id)
              .then(handleSuccessUpdateLoan(property), utils.handleServerError);
          };
        };
      };

      $scope.cancelEdit = function(property) {
        if (property === 'branch') {
          $scope.loan.branch = $scope.originalValues[property];
        } else {
          $scope.loan[property] = $scope.originalValues[property];
        }
        $scope.originalValues[property] = undefined;
      };

      $scope.deleteLoan = function() {
        var del = confirm("Do you really want to delete this loan?");
        if (del) {
          loanSrv.deleteLoan($scope.loan.id)
            .then(handleSuccessDeleteLoan, utils.handleServerError);
        };
      };

      function handleSuccessUpdateLoan(property) {
        return function(response) {
          $scope.originalValues[property] = undefined;
          $timeout(function() {
            $scope.loan[property] = response.data[property];
          });
        };
      };

      function handleSuccessUpdateLoanBranch(response) {
        $scope.originalValues['branch'] = undefined;
        $timeout(function() {
          $scope.loan.branch = response.data;
        });
      };

      function handleSuccessDeleteLoan(response) {
        $scope.loan = {};
        $location.path("/loans/list");
      };

      function handleSuccessGetBranchList(response) {
        $scope.branches = response.data;
      };

      $scope.addOwner = function() {
        $location.path("/loans/" + $routeParams.loanId +
          "/owners/add");
      };

      $scope.remove = function(ownerId) {
        loanSrv.removeLoanOwner($routeParams.loanId, ownerId)
          .then(handleSuccessRemoveLoanOwner, utils.handleServerError);
      };

      function handleSuccessRemoveLoanOwner(response) {
        $scope.owner = {};
        $location.path("/loans/" + $routeParams.loanId);
      };
    }
  ]);

  bankApp.controller('AddLoanOwnerCtrl', ['$scope', '$location',
    '$routeParams', '$timeout', 'utils', 'customerSrv', 'loanSrv',
    function($scope, $location, $routeParams, $timeout, utils,
      customerSrv, loanSrv) {
      init();

      function init() {
        $scope.customer = {};
        $scope.customerList = [];
        this.loanOwners = [];
        loanSrv.getLoanOwners($routeParams.loanId)
          .then(handleSuccessGetLoanOwners, utils.handleServerError);
        customerSrv.getListCustomers()
          .then(handleSuccessGetListCustomers, utils.handleServerError);
      };

      $scope.addOwner = function(valid) {
        if (valid) {
          loanSrv.addLoanOwner({ 'id': $scope.customer.id },
              $routeParams.loanId)
            .then(handleSuccessAddLoanOwner, utils.handleServerError);
        }
      };

      function handleSuccessAddLoanOwner(response) {
        $scope.customer = {};
        alert('Owner added!');
        $location.path("/loans/" + $routeParams.loanId);
      };

      function handleSuccessGetLoanOwners(response) {
        this.loanOwners = response.data;
      };

      function isOwner(customer) {
        for (var i = this.loanOwners.length - 1; i >=
          0; i--) {
          if (this.loanOwners[i].id === customer.id)
            return false;
        };
        return true;
      };

      function handleSuccessGetListCustomers(response) {
        $timeout(function() {
          $scope.customerList = response.data.filter(isOwner);
        }, 100);
      };
    }
  ]);
})(window, document);