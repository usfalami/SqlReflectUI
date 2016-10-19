/**
 * 
 */

angular.module('app')

.config(['$routeProvider', function($routeProvider) {
	
	$routeProvider

	.when('/home', 				{templateUrl: 'html/home.html', controller: 'consult'})
	.when('/databases', 		{templateUrl: 'html/databases.html', controller: 'consult'})
	.when('/tables', 			{templateUrl: 'html/tables.html', controller: 'consult'})
	.when('/views', 			{templateUrl: 'html/views.html', controller: 'consult'})
	.when('/columns', 			{templateUrl: 'html/columns.html', controller: 'consult'})
	.when('/primaryKey', 		{templateUrl: 'html/primary_key.html', controller: 'consult'})
	.when('/procedures',		{templateUrl: 'html/procedures.html', controller: 'consult'})
	.when('/arguments', 		{templateUrl: 'html/arguments.html', controller: 'consult'})
	.when('/nativeFunctions', 	{templateUrl: 'html/native_functions.html', controller: 'consult'})
	.when('/rows', 				{templateUrl: 'html/rows.html', controller: 'consult'})
	.when('/headers', 			{templateUrl: 'html/headers.html', controller: 'consult'})
	.otherwise({redirectTo: '/home'});

}]);