/*jslint nomen: true */
/*global $, document, _ */
'use strict';
$(document).ready(function() {	
    /* Hide error text on input */
    $(".validate").keydown(function() {
    	$(this).closest('.form-group').removeClass('has-error');
    	$(this).closest('.form-group').find('.control-label').hide().empty();
    });
});

function validate() {
    var missingFields = [],
        requiredFields = [{
            id: 'seedUrl',
            message: 'Please enter a seed URL (e.g. www.cnn.com)'
        }, {
            id: 'searchTerms',
            message: 'Please enter search terms'
        }];
    
	/* Find missing required fields */
    _.each(requiredFields, function(field) {
        var value = $.trim($('#' + field.id).val());
        if (!value) {
        	missingFields.push(field);
        }
    });
       
    if (missingFields.length > 0) {
    	/* Show message for each missing required field */
        _.each(missingFields, function(field) {
        	$('#' + field.id).closest('.form-group').addClass('has-error')
        	$('#' + field.id).closest('.form-group').find('.control-label').text(field.message).show();
        });
    	/* Set focus to first missing field */
    	$('#' + missingFields[missingFields.length-1].id).focus();
        return false;
    }
    
    return true;
}