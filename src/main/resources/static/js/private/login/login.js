var login = angular.module('login',['ngRoute']);

login.config(['$routeProvider',function ($routeProvider) {
    $routeProvider.when('/',{templateUrl:"html/login/loginContent.html",controller:LoginController})
}]);


function LoginController($scope,$http,$window,$rootScope) {

    //登录验证
    $scope.login = function (Admin) {

        if (Admin.username == undefined || Admin.username == "") {
            alert("用户名不能为空");
            return;
        } else if (Admin.password == undefined || Admin.password == "") {
            alert("密码不能为空");
            return;
        }

        var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
        if (emailReg.test( Admin.username )) {
            Admin.email = Admin.username;
        }

        $http.post("/admin/login",Admin)
            .then(function (response) {
                var status = response.data.data.status;
                var msg = response.data.data.message;

                if (status == 200){
                    if (response.data.data.roleId == "2"){
                        $window.location = "index.html";
                    } else if (response.data.data.roleId == "1") {
                        alert("管理员");
                    }
                } else if (status == 201){
                    alert(msg);
                }

            })

    }


    //退出登录
    $scope.quit = function () {

        $http.post("/admin/logOut")
            .then(function (response) {
                var status = response.data.data.status;
                var msg = response.data.data.message;

                if (status == 200){
                    console.log(msg);
                } else {
                    alert("退出失败");
                }
            })

    }


}
