'use strict';

angular
		.module('angularjsApp')
		.directive(
				'treeMembers',
				function($compile) {
					var tree = new Tree({
						margin : 20,
						elHeight : 40,
						elWidth : 150,
						createNodeElement : function(m) {
							return angular.element('<div ng-click="onMemberClick(' + m[0] + ')">' + m[3] + '</div>')[0];
						}
					});
					return {
						template : '<div id="{{containerId}}" style="position: relative;"><div id="{{containerId}}_ancestors" class=""></div><div id="{{containerId}}_descendants" class=""></div></div>',
						restrict : 'E',
						transclude: true,
						scope : {
							containerId : '@id',
							members : '=',
							query : '=',
							orientation : '='
						},
						link: function (scope, ele, attrs) {
							var updateTree = function() {
								console.log('scope', scope);
								if (!scope.query) {
									return;
								}
								tree.create(scope.query, scope.containerId, scope.members, scope.orientation);
								$compile(ele.contents())(scope.$parent);
							};
							scope.$watch('query', updateTree);
							scope.$watch('orientation', updateTree);
						}
					};
				});
angular.module('angularjsApp').controller(
		'MainCtrl',
		function($scope, $http, ngDialog) {
			$scope.members = [ [ 1, 2, 3, 'Bhavesh' ], [ 18, 1, 22, 'Aryan' ], [ 3, null, null, 'Mrudula' ], [ 4, null, null, 'Ketan' ], [ 5, 2, 3, 'Jinisha' ],
								[ 6, 4, 5, 'Rina' ], [ 7, 4, 5, 'Neha' ], [ 8, 4, 5, 'Ritika' ], [ 9, 19, null, 'Naranji' ], [ 2, 9, 21, 'Dilip' ], [ 10, 9, 21, 'Jaman' ],
								[ 11, 9, 21, 'Hemalatha' ], [ 12, 9, 21, 'Kala' ], [ 14, 11, null, 'Dharmesh' ], [ 13, 11, null, 'Raju' ], [ 15, 11, null, 'Dinesh' ],
								[ 16, 13, null, 'Bhavini' ], [ 17, 13, null, 'Tina' ], [ 19, null, null, 'N_Father' ], [ 20, 19, null, 'N_Brother' ], [ 21, null, null, 'Zaver' ],
								[ 22, null, null, 'Neha Mami' ] ];
			/*
			 * $http.get('/rest/members').success(function(data) { //
			 * $scope.members = data; console.log('Members rest call
			 * successful.'); });
			 */
			$scope.onAddOpen = function(member) {
				if (member) {
					// populate form with member information
				}
				ngDialog.open({
					template : 'addMemberTemplate',
					scope : $scope,
					controller : 'MainCtrl'
				});
			};
			$scope.onAdd = function(member) {
				console.log('Member: ', member);
			};
			$scope.onQueryChange = function() {
				console.log('New Query: ', $scope.query);
			};
			$scope.onMemberClick = function(memberId) {
				alert('Member clicked: ' + memberId);
			};
		});

/**
 * @ngdoc function
 * @name angularjsApp.controller:MainCtrl
 * @description # AddCtrl Controller of the angularjsApp
 */
angular.module('angularjsApp').controller(
		'TreeCtrl',
		function($scope) {
			$scope.members = [ [ 1, 2, 3, 'Bhavesh' ], [ 18, 1, 22, 'Aryan' ], [ 3, null, null, 'Mrudula' ], [ 4, null, null, 'Ketan' ], [ 5, 2, 3, 'Jinisha' ],
								[ 6, 4, 5, 'Rina' ], [ 7, 4, 5, 'Neha' ], [ 8, 4, 5, 'Ritika' ], [ 9, 19, null, 'Naranji' ], [ 2, 9, 21, 'Dilip' ], [ 10, 9, 21, 'Jaman' ],
								[ 11, 9, 21, 'Hemalatha' ], [ 12, 9, 21, 'Kala' ], [ 14, 11, null, 'Dharmesh' ], [ 13, 11, null, 'Raju' ], [ 15, 11, null, 'Dinesh' ],
								[ 16, 13, null, 'Bhavini' ], [ 17, 13, null, 'Tina' ], [ 19, null, null, 'N_Father' ], [ 20, 19, null, 'N_Brother' ], [ 21, null, null, 'Zaver' ],
								[ 22, null, null, 'Neha Mami' ] ];
			$scope.orientation = 'vertical';
			$scope.onMemberClick = function(memberId) {
				console.log('Member clicked: ' + memberId);
			};
		});

angular.module('angularjsApp').controller('AddCtrl', function() {});
