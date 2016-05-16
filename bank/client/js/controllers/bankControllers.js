var bankControllers = angular.module('bankControllers', []);

bankControllers.controller('NavBarCtrl', ['$scope', function(
  $scope) {
  this.tab = 1;
  this.selectTab = function(setTab){
    console.log(setTab);
    this.tab = setTab;
  };
  this.isSelected = function(checkTab){
    return this.tab === checkTab;
  };
}]);

bankControllers.controller('CustomerListCtrl', ['$scope', '$http', function(
  $scope, $http) {
  var url = "http://localhost:9192/customers";
  $scope.customers = [];
  $http.get(url)
    .success(function(response) {
      $scope.customers = response;
    });
}]);

bankControllers.controller('AccountListCtrl', ['$scope', '$http', function(
  $scope, $http) {
  var url = "http://localhost:9192/accounts";
  $scope.accounts = [];
  $http.get(url)
    .success(function(response) {
      $scope.accounts = response;
    });
}]);