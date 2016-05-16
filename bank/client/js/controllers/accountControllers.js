var accountControllers = angular.module('accountControllers', []);

function genericErrorCallback(response) {
  console.log(response);
  var title = response.status + " " + response.statusText + ": " + response.data
    .error.title;
  var message = response.data.error.message;
  var rld = confirm(title + "\n" + message + "\n" + "Refresh?");
  if (rld)
    location.reload(true);
};

generalControllers.controller('AccountListCtrl', ['$scope', '$http', function(
  $scope, $http) {
  var url = "http://localhost:9192/accounts";
  $scope.accounts = [];
  $http.get(url)
    .then(function(response) {
      $scope.accounts = response.data;
    }, genericErrorCallback);
}]);