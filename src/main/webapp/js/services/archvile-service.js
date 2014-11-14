var svc = angular.module('archvileService', []);

svc.service('ArchvileService', function ($http) {

  var statsCache = {};

  function success(response) {
    statsCache.data = response.data;
    statsCache.lastUpdated = new Date().getTime();
    return statsCache;
  }

  function error(response) {
    return response.status;
  }

  function startArchvile(parameters) {
    var request = $http({
      url: '/archvile/start',
      method: 'POST',
      headers: {
          'Content-Type': 'application/json; charset=utf-8'
      },
      data: parameters
    });
    return (request.then(success, error));
  }

  function stopArchvile() {
    var request = $http({
        url: '/archvile/stop',
        method: 'POST'
    });
    return (request.then(success, error));
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
     startArchvile: startArchvile,
      stopArchvile: stopArchvile,
     getStatistics: getStatistics,
    loadStatistics: loadStatistics
  });

});
