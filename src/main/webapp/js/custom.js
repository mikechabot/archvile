$(document).ready(function () {
  "use strict";
  /* Hide error text on input */
  $("#seedUrl").keydown(function () {
    $('#error').slideUp(500);
  });
});

function validate() {
  var seedUrl = $.trim($("#seedUrl").val());
  if (!seedUrl) {
    $('#error').text("You must enter a seed URL (e.g. www.cnn.com) ").slideDown(500);
    return false;
  }
  return true;
}