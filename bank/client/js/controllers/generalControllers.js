(function(window, document, undefined) {
  bankApp.controller('NavBarCtrl', ['$scope', function(
    $scope) {
    $scope.tab = 1;
    $scope.selectTab = function(setTab) {
      $scope.tab = setTab;
    };
    $scope.isSelected = function(checkTab) {
      return $scope.tab === checkTab;
    };
  }]);

  bankApp.controller('OrderCtrl', ['$scope', function($scope) {
    $scope.predicate = '';
    $scope.reverse = false;
 
    $scope.init = function(predicate, reverse) {
      $scope.predicate = predicate;
      $scope.reverse = (reverse === undefined) ? false : reverse;
    };

    $scope.isOrdered = function(predicate) {
      return $scope.predicate === predicate;
    };

    $scope.order = function(predicate) {
      $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse :
        false;
      $scope.predicate = predicate;
    };
  }]);
})(window, document);