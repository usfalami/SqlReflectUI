/**
 * 
 */

angular.module('app')

.config(['$routeProvider', function($routeProvider) {
	
	$routeProvider

	.when('/home', 																{templateUrl: 'html/home.html', controller: 'consult'})
	.when('/databases/:databasePattern?', 										{templateUrl: 'html/databases.html', controller: 'consult'})
	.when('/tables/:databasePattern?/:tablePattern?', 							{templateUrl: 'html/tables.html', controller: 'consult'})
	.when('/views/:databasePattern?/:tablePattern?', 							{templateUrl: 'html/views.html', controller: 'consult'})
	.when('/columns/:databasePattern?/:tablePattern?/:columnPattern?',			{templateUrl: 'html/columns.html', controller: 'consult'})
	.when('/primaryKeys/:databasePattern?/:tablePattern?',						{templateUrl: 'html/primary_key.html', controller: 'consult'})
	.when('/procedures/:databasePattern?/:procedurePattern?',					{templateUrl: 'html/procedures.html', controller: 'consult'})
	.when('/arguments/:databasePattern?/:procedurePattern?/:argumentPattern?',	{templateUrl: 'html/arguments.html', controller: 'consult'})
	.when('/nativeFunctions', 													{templateUrl: 'html/native_functions.html', controller: 'consult'})
	.when('/rows', 																{templateUrl: 'html/rows.html', controller: 'consult'})
	.when('/headers', 															{templateUrl: 'html/headers.html', controller: 'consult'})
	.otherwise({redirectTo: '/home'});

}]);