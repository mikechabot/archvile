var svc = angular.module('indexService', []);

svc.service('IndexService', function ($http) {

  var indexCache = {};

  function success(response) {
    indexCache.data = response.data;
    indexCache.lastUpdated = new Date().getTime();
    return indexCache;
  }

  function error(response) {
    return response.status;
  }

  function loadIndex() {
    var request = $http({
      url: '/index',
      async: true,
      method: 'GET'
    });
    return (request.then(success, error));
  }

  function getIndex() {
    return indexCache;
  }

  function deleteIndex() {
    var request = $http({
      url: '/index',
      method: 'DELETE'
    });
    return (request.then(success, error));
  }

  return ({
    getIndex: getIndex,
    loadIndex: loadIndex,
    deleteIndex: deleteIndex
  });

});
