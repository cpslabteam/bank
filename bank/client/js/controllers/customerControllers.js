(function(window, document, undefined) {
  bankApp.controller('CustomerListCtrl', ['$scope', '$location', 'utils',
    'customerSrv',
    function(
      $scope, $location, utils, customerSrv) {
      $scope.customers = [];
      customerSrv.getListCustomers()
        .then(handleSuccess, utils.handleServerError);

      function handleSuccess(response) {
        $scope.customers = response.data;
      };

      $scope.details = function(customerId) {
        $location.path('customers/' + customerId);
      };
    }
  ]);

  bankApp.controller('CreateCustomerCtrl', ['$scope', '$location', 'utils',
    'customerSrv',
    function($scope, $location, utils, customerSrv) {
      $scope.customer = {};

      $scope.create = function(valid) {
        if (valid) {
          customerSrv.createCustomer($scope.customer)
            .then(handleSuccess, utils.handleServerError);
        }
      };

      function handleSuccess(response) {
        $scope.customer = {};
        alert("Customer created!");
        $location.path("/customers");
      };
    }
  ]);

  bankApp.controller('CustomerDetailsCtrl', ['$scope', '$routeParams',
    '$location', 'utils', 'customerSrv',
    function($scope, $routeParams, $location, utils, customerSrv) {
      init();

      function init() {
        $scope.customer = {};
        customerSrv.getCustomer($routeParams.customerId)
          .then(handleSuccessGetCustomer, utils.handleServerError);
        $scope.originalValues = [];
      };

      function handleSuccessGetCustomer(response) {
        $scope.customer = response.data;
      };

      $scope.hasAccounts = function() {
        return $scope.customer.accounts !== undefined && $scope.customer
          .accounts.length > 0;
      };

      $scope.hasLoans = function() {
        return $scope.customer.loans !== undefined && $scope.customer
          .loans.length > 0;
      };

      $scope.isEditable = function(property) {
        return $scope.originalValues[property] !== undefined;
      };

      $scope.setEditable = function(property) {
        $scope.originalValues[property] = $scope.customer[property];
      };

      $scope.saveEdit = function(valid, property) {
        if (valid) {
          var update = {};
          update[property] = $scope.customer[property];
          customerSrv.updateCustomer(update, $scope.customer.id)
            .then(handleSuccessUpdateCustomer(property), utils.handleServerError);
        };
      };

      $scope.cancelEdit = function(property) {
        $scope.customer[property] = $scope.originalValues[property];
        $scope.originalValues[property] = undefined;
      };

      $scope.deleteCustomer = function() {
        var del = confirm("Do you really want to delete this user?");
        if (del) {
          customerSrv.deleteCustomer($scope.customer.id)
            .then(handleSuccessDeleteCustomer, utils.handleServerError);
        };
      };

      function handleSuccessUpdateCustomer(property) {
        return function(response) {
          $scope.originalValues[property] = undefined;
          $scope.customer[property] = response.data[property];
        }
      };

      function handleSuccessDeleteCustomer(response) {
        $scope.customer = {};
        $location.path("/customers/list");
      };
    }
  ]);
})(window, document);