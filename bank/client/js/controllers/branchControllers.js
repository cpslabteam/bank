var branchControllers = angular.module('branchControllers', []);

function genericErrorCallback(response) {
  console.log(response);
  var title = response.status + " " + response.statusText + ": " + response.data
    .error.title;
  var message = response.data.error.message;
  var rld = confirm(title + "\n" + message + "\n" + "Refresh?");
  if (rld)
    location.reload(true);
};

generalControllers.controller('BranchListCtrl', ['$scope', '$http', function(
  $scope, $http) {
  var url = "http://localhost:9192/branches";
  $scope.branches = [];
  $http.get(url)
    .then(function(response) {
      $scope.branches = response.data;
    }, genericErrorCallback);
}]);