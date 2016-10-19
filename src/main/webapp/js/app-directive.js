/**
 * 
 */

angular.module('app')

.directive('actionTimer', function() {
	return {
		scope: {timer: '='},
		templateUrl: 'html/web_component/action_timer.html'
	};
});