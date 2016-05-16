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
})(window, document);