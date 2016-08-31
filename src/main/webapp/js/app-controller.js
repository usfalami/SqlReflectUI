/**
 * 
 */

angular.module('app')

.controller('consult', function($scope, $http){
	$scope.data = {};

	$scope.submit = function(uri){
		
		console.log($scope.data);
		
		$scope.searching = true;
		$scope.rows = null;
		$scope.perf = null;
		
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
});