var svc = angular.module('archvileService', []);

svc.service('ArchvileService', function ($http, $rootScope) {

  var isRunning;

  function success(response) {
    isRunning = response.data.isRunning;
    $rootScope.$broadcast('isRunning', isRunning);
    return isRunning;
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

  function isRunning() {
    var request = $http({
      url: '/archvile/status',
      method: 'POST'
    });
    return (request.then(success, error));
  }

  return ({
     startArchvile: startArchvile,
      stopArchvile: stopArchvile,
         isRunning: isRunning
  });

});
