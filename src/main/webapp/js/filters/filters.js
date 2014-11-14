var filters = angular.module('filters', []);

filters.filter('yesNo', function () {
  return function (input) {
    return (input === true) ? 'Yes' : 'No';
  };
});

filters.filter('emptyString', function () {
  return function (input) {
    return input || 'None';
  };
});

filters.filter('longToDate', function () {
  return function (input) {
    var date = new Date(input);
    var month, day, year, hour, minute, second;
    
    month = date.getMonth()+1;
    day = date.getDate();
    year = date.getFullYear();
    hour = getHour(date);
    minute = addZero(date.getMinutes());
    second = addZero(date.getSeconds());
    
    return month + '/' + day + '/' + year + ' ' + hour.digit + ':' + minute + ':' + second + ' ' + hour.amPm;
  };

  function getHour(date) {
    var hour = {};
    hour.digit = date.getHours();
    if (hour.digit > 12) {
      hour.digit = hour.digit - 12;
      hour.amPm = 'PM';
    } else if (hour.digit === 12) {
      hour.digit = hour.digit;
      hour.amPm = 'PM';
    } else {
      hour.digit = hour.digit;
      hour.amPm = 'AM';
    }
    return hour;
  }

  function addZero(time) {
    if (time < 10) time = '0' + time;
    return time;
  }
});