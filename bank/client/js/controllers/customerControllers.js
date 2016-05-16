var customerControllers = angular.module('customerControllers', []);

function genericErrorCallback(response) {
  console.log(response);
  var title = response.status + " " + response.statusText + ": " + response.data
    .error.title;
  var message = response.data.error.message;
  var rld = confirm(title + "\n" + message + "\n" + "Refresh?");
  if (rld)
    location.reload(true);
};

generalControllers.controller('CustomerListCtrl', ['$scope', '$http', function(
  $scope, $http) {
  var url = "http://localhost:9192/customers";
  $scope.customers = [];
  $http.get(url)
    .then(function success(response) {
      $scope.customers = response.data;
    }, genericErrorCallback);
}]);