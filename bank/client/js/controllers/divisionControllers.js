(function(window, document, undefined) {
  bankApp.controller('DivisionListCtrl', ['$scope', '$location', 'utils',
    'divisionSrv',
    function(
      $scope, $location, utils, divisionSrv) {
      $scope.divisions = [];
      divisionSrv.getListDivisions()
        .then(handleSuccess, utils.handleServerError);

      function handleSuccess(response) {
        $scope.divisions = response.data;
      };

      $scope.createDivision = function() {
        $location.path("/divisions/create");
      };

      $scope.details = function(divisionId) {
        $location.path('/divisions/' + divisionId);
      };
    }
  ]);

  bankApp.controller('CreateDivisionCtrl', ['$scope', '$location', 'utils',
    'divisionSrv',
    function($scope, $location, utils, divisionSrv) {
      $scope.division = {};

      $scope.create = function(valid) {
        if (valid) {
          divisionSrv.createDivision($scope.division)
            .then(handleSuccess, utils.handleServerError);
        }
      };

      function handleSuccess(response) {
        $scope.division = {};
        alert("Division created!");
        $location.path("/divisions/list");
      };
    }
  ]);

  bankApp.controller('DivisionDetailsCtrl', ['$scope', '$routeParams',
    '$location', '$timeout', 'utils', 'divisionSrv',
    function($scope, $routeParams, $location, $timeout, utils,
      divisionSrv) {
      init();

      function init() {
        $scope.division = {};
        $scope.originalValues = [];
        divisionSrv.getDivision($routeParams.divisionId)
          .then(handleSuccessGetDivision, utils.handleServerError);
        divisionSrv.getDivisionBranches($routeParams.divisionId)
          .then(handleSuccessGetDivisionBranches, utils.handleServerError);
      };

      function handleSuccessGetDivision(response) {
        $timeout(function() {
          $scope.division = response.data;
        });
      };

      function handleSuccessGetDivisionBranches(response) {
        $timeout(function() {
          $scope.division.branches = response.data;
        }, 100);
      };

      $scope.hasBranches = function() {
        return $scope.division.branches !== undefined && $scope.division
          .branches.length > 0;
      };

      $scope.isEditable = function(property) {
        return $scope.originalValues[property] !== undefined;
      };

      $scope.setEditable = function(property) {
        $scope.originalValues[property] = $scope.division[property];
      };

      $scope.saveEdit = function(valid, property) {
        if (valid) {
          var update = {};
          update[property] = $scope.division[property];
          divisionSrv.updateDivision(update, $scope.division.id)
            .then(handleSuccessUpdateDivision(property), utils.handleServerError);
        };
      };

      $scope.cancelEdit = function(property) {
        $scope.division[property] = $scope.originalValues[property];
        $scope.originalValues[property] = undefined;
      };

      $scope.deleteDivision = function() {
        var del = confirm("Do you really want to delete this division?");
        if (del) {
          divisionSrv.deleteDivision($scope.division.id)
            .then(handleSuccessDeleteDivision, utils.handleServerError);
        };
      };

      function handleSuccessUpdateDivision(property) {
        return function(response) {
          $scope.originalValues[property] = undefined;
          $timeout(function() {
            $scope.division[property] = response.data[property];
          });
        };
      };

      function handleSuccessDeleteDivision(response) {
        $scope.division = {};
        $location.path("/divisions/list");
      };
    }
  ]);
})(window, document);