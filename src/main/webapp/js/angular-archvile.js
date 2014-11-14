var app = angular.module('archvile', ['archvileService', 'indexService', 'filters']);

app.directive('archvile', function () {
  return {
    restrict: 'A',
    controller: function ($scope, ArchvileService) {
      /* Set scope data */
      function setStatistics(statistics) {
        $scope.statistics = statistics.data;
        $scope.lastUpdated = statistics.lastUpdated;
        $scope.isRunning = $scope.statistics.isRunning;
      }
      /* Load statistics from the server */
      $scope.loadStatistics = function loadStatistics() {
        ArchvileService.loadStatistics().
          then(function (statistics) {
            setStatistics(statistics);
          });
      };
      /* Load statistics when the directive is initialized */
      $scope.loadStatistics();
    }
  };
});

app.directive('statistics', function () {
  return {
    restrict: 'E',
    require: '^archvile',
    templateUrl: 'templates/statistics.html',
    controller: function ($scope, $timeout) {

      var glyphClass = {
        idle: 'glyphicon-refresh',
        active: 'glyphicon-download-alt'
      };

      $scope.refreshClass = glyphClass.idle;

      /* Refresh statistics */
      $scope.refresh = function () {
        $scope.refreshClass = glyphClass.active;
        $timeout(function () {
          $scope.loadStatistics();
          $scope.refreshClass = glyphClass.idle;
        }, 100);
      };
    }
  };
});

app.directive('controls', function () {
  return {
    restrict: 'E',
    require: '^archvile',
    templateUrl: 'templates/controls.html',
    controller: function ($scope, ArchvileService) {
      /* Start Archvile */
      $scope.start = function () {
        /* Set request parameters */
        var parameters = {
          searchTerms: $scope.searchTerms,
          seedUrl: $scope.seedUrl
        };
        ArchvileService.startArchvile(parameters).
          then(function (results) {
            if (results.data.success !== true) {
              console.log('Unable to start Archvile...');
            } else {
              $scope.loadStatistics();
              console.log('Started Archvile...');
            }
          });
      };
      /* Stop Archvile */
      $scope.stop = function () {
        ArchvileService.stopArchvile().
          then(function (results) {
            if (results.data.success !== true) {
              console.log('Unable to stop Archvile...');
            } else {
              $scope.loadStatistics();
              console.log('Stopped Archvile...');
            }
          });
      };
    }
  };
});

app.directive('index', function () {
  return {
    restrict: 'E',
    templateUrl: 'templates/index.html',
    controller: function ($scope, IndexService) {
      /* Set scope data */
      function setIndex(index) {
        $scope.index = index.data;
        $scope.lastUpdated = index.lastUpdated;
      }
      /* Load statistics from the server */
      $scope.loadIndex = function loadIndex() {
        IndexService.loadIndex().
          then(function (index) {
            setIndex(index);
          });
      };
      /* Load statistics when the directive is initialized */
      $scope.loadIndex();
    }
  };
});