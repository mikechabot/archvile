var svc = angular.module('statisticsService', []);

svc.service('StatisticsService', function ($http) {

  var statsCache = {};

  function success(response) {
    statsCache.data = response.data;
    statsCache.lastUpdated = new Date().getTime();
    return statsCache;
  }

  function error(response) {
    return response.status;
  }

  function loadStatistics() {
    var request = $http({
      url: '/archvile/statistics',
      method: 'GET'
    });
    return (request.then(success, error));
  }

  function getStatistics() {
    return statsCache;
  }

  return ({
    getStatistics: getStatistics,
    loadStatistics: loadStatistics
  });

});
