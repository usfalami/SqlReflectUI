/**
 * 
 */

angular.module('app')

.config(['$routeProvider', function($routeProvider) {
	$routeProvider.

	when('/home', {
		templateUrl: 'html/home.html', controller: 'crt1'
	}).
	
	when('/databases', {
		templateUrl: 'html/databases.html', controller: 'crt1'
	}).

	when('/tables', {
		templateUrl: 'html/tables.html', controller: 'crt1'
	}).

	when('/procedures', {
		templateUrl: 'html/procedures.html', controller: 'crt1'
	}).

	when('/columns', {
		templateUrl: 'html/columns.html', controller: 'crt1'
	}).
	
	when('/rows', {
		templateUrl: 'html/rows.html', controller: 'crt1'
	}).

	when('/headers', {
		templateUrl: 'html/headers.html', controller: 'crt1'
	}).
	
	otherwise({
		redirectTo: '/home'
	});

}]);