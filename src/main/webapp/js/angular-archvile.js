var app = angular.module('archvile', ['archvileService', 'indexService', 'statisticsService', 'filters', 'ui.bootstrap']);

app.controller('SearchCtrl', function ($scope, $interval, ArchvileService) {

  /* Start Archvile */
  $scope.start = function () {
    /* Set request parameters */
    var parameters = {
      searchTerms: ($scope.isSearchDisabled) ? null : $scope.searchTerms,
      seedUrl: $scope.seedUrl
    };
    ArchvileService.startArchvile(parameters).
      then(function (isRunning) {
        $scope.isRunning = isRunning;
        if (!$scope.isRunning) console.log('Unable to start archvile...');
      });
  };

  /* Stop Archvile */
  $scope.stop = function () {
    ArchvileService.stopArchvile().
      then(function (isRunning) {
        $scope.isRunning = isRunning;
        if ($scope.isRunning) console.log('Unable to stop archvile...');
      });
  };

  $interval(function () {
    ArchvileService.isRunning().
      then(function (isRunning) {
        $scope.isRunning = isRunning;
      });
  }, 5000);

  $scope.isSearchDisabled = false;

});

app.controller('StatsCtrl', function ($scope, $timeout, $interval, StatisticsService) {
  /* Set statistics data */
  function setStatistics(statistics) {
    $scope.statistics = statistics.data;
    $scope.lastUpdated = statistics.lastUpdated;
  }

  /* Load statistics from the server */
  $scope.loadStatistics = function loadStatistics(showLoading) {
    if (showLoading) $scope.isLoading = true;
    $timeout(function () {
      StatisticsService.loadStatistics().
        then(function (statistics) {
          setStatistics(statistics);
        }).
        finally(function () {
          $scope.isLoading = false;
        });
    }, 200);
  };

  $scope.refreshStatistics = function refreshStatistics() {
    $scope.loadStatistics();
  }

  /* Listen for broadcasts */
  $scope.$on('isRunning', function(event, isRunning) {
    $scope.isRunning = isRunning;
    $scope.loadStatistics();
  });

  /* Load stats when the controller initializes */
  $scope.loadStatistics();

});

app.controller('IndexCtrl', function ($scope, $timeout, IndexService) {
  /* Set index data */
  function setIndex(index) {
    $scope.index = index.data;
    $scope.lastUpdated = index.lastUpdated;
  }

  /* Load index from the server */
  $scope.loadIndex = function() {
    $scope.isLoading = true;
    $timeout(function () {
      IndexService.loadIndex().
        then(function (index) {
          setIndex(index);
        }).
        finally(function () {
          $scope.isLoading = false;
        });
    }, 0);
  };

  /* Delete the index */
  $scope.deleteIndex = function () {
    IndexService.deleteIndex();
    $scope.loadIndex();
  };

  /* Listen for broadcasts */
  $scope.$on('isRunning', function(event, isRunning) {
    $scope.isRunning = isRunning;
  });

  /* Load index when the controller initializes */
  $scope.loadIndex();

});