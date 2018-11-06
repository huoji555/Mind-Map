var mind = angular.module('mind',['ngRoute']);

mind.config(['$routeProvider',function ($routeProvider) {
    $routeProvider.when('/',{templateUrl:"html/mind/mindContent.html",controller:MindController})
}]);


function MindController($scope,$http,$window,$rootScope) {

    var jm = null;

    //新建一个知识图谱
    $scope.open_empty = function () {
        var options = {
            container : 'jsmind_container',
            theme : 'greensea',
            editable : true
        }
        jm = new jsMind(options);
        jm.show();
    }

    $scope.open_empty();

}