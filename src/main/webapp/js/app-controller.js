/**
 * 
 */

angular.module('app')

.controller('consult', function($scope, $http, $routeParams){
	
	$scope.submit = function(uri){
		$scope.reset();
		$scope.searching = true;
		$http({url: uri, method: "GET", params: $scope.data})
		
			.success(function(data, status){
				$scope.searching = false;
				$scope.head = data.columns;
				$scope.rows = data.items;
				$scope.perf = data.timePerform;
			})
			.error(function(error, status){
				$scope.searching = false;
				console.log(status, error);
			});
	};
	
	$scope.reset = function(){
		$scope.head = null;
		$scope.rows = null;
		$scope.perf = null;
	};
});