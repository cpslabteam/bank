(function(window, document, undefined) {
  bankApp.controller('NavBarCtrl', ['$scope', '$location', 'AuthenticationService',
    function(
      $scope, $location, AuthenticationService) {
      $scope.tab = 1;
      $scope.selectTab = function(setTab) {
        $scope.tab = setTab;
      };
      $scope.isSelected = function(checkTab) {
        return $scope.tab === checkTab;
      };
      $scope.isAuthenticated = function() {
        return AuthenticationService.IsAuthenticated();
      };
      $scope.logout = function(){
        AuthenticationService.ClearCredentials();
        $location.path('/login');
      };
    }
  ]);

  bankApp.controller('LoginController', ['$scope', '$rootScope', '$location',
    'AuthenticationService', 'utils',
    function($scope, $rootScope, $location, AuthenticationService, utils) {
      // reset login status
      AuthenticationService.ClearCredentials();

      $scope.login = function() {
        AuthenticationService.Login($scope.username, $scope.password)
          .then(handleSuccessLogin, function (response) {
            $scope.error = "username and password combination does not exist";
          });
      };

      function handleSuccessLogin(response) {
        AuthenticationService.SetCredentials($scope.username, $scope.password);
        $location.path('/');
      };
    }
  ]);
})(window, document);