'use strict';

/**
 * @ngdoc function
 * @name angularjsApp.controller:MainCtrl
 * @description # MainCtrl Controller of the angularjsApp
 */
angular.module('angularjsApp').controller('MainCtrl', function($scope) {
	alert('Query: ' + $scope.query);
	$scope.members = [ {
		'firstName' : 'Bhavesh',
		'lastName' : 'Thakker'
	} ];
});
