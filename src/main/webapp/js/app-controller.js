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
				$scope.rows = data.list; //@see @XmlElementWrapper(name="items")
				$scope.perf = data.actionTimer;
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

	$scope.data = {};
	for (var p in $routeParams) {
	    if ($routeParams.hasOwnProperty(p)){
	    	$scope.data[p] = $routeParams[p];
	    }
	}
	
});