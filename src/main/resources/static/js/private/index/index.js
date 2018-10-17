var indexApp = angular.module('index',['ngRoute']);

indexApp.config(['$routeProvider',function ($routeProvider) {
    $routeProvider.when('/',{templateUrl:"html/index/indexContent.html",controller:indexController})
}]);



/*---------------------------------------首页controller--------------------------------*/
indexApp.controller('indexCon',function ($scope,$http,$window,$rootScope) {

    //退出登录
    $rootScope.quit = function () {

        $http.post("/admin/logOut")
            .then(function (response) {
                var status = response.data.status;
                var msg = response.data.message;

                if (status == 200){
                    $window.location = "login.html";
                } else {
                    alert("退出失败");
                }
            })

    }

    /*判断是否登录*/
    $rootScope.ifLogin = function () {

        $http.post("/admin/ifLogin")
            .then(function (response) {
                var status = response.data.status;
                var msg = response.data.message;

                if (status == 201){
                    alert("您还未登录，请登录后再操作");
                    $window.location = "login.html";
                }
            })
    }

    $rootScope.ifLogin();
    $rootScope.first = '票据管理';


    /*动态赋值*/
    $rootScope.changeName = function (first,second) {
        $rootScope.first = first;
    }


});

function indexController($scope,$http,$window,$rootScope) {

}

