(function(window, document, undefined) {
  bankApp.controller('CustomerListCtrl', ['$scope', 'utils', 'customerSrv',
    function(
      $scope, utils, customerSrv) {
      $scope.customers = [];
      customerSrv.getListCustomers()
        .then(handleSuccess, utils.handleServerError);

      function handleSuccess(response) {
        $scope.customers = response.data;
      }
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
})(window, document);