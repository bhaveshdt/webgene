'use strict';

/**
 * @ngdoc function
 * @name angularjsApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the angularjsApp
 */
angular.module('angularjsApp')
  .controller('AboutCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
