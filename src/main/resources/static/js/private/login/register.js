var regsiter = angular.module('register',['ngRoute']);

regsiter.config(['$routeProvider',function ($routeProvider) {
    $routeProvider.when('/',{templateUrl:'html/login/registerContent.html', controller:RegsiterController})
}]);


function RegsiterController($scope,$http,$window,$rootScope,$timeout) {


    //请求注册
    $scope.register = function (reg) {

        //还没进行验证,先填数据
    	var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    	
        if (!(emailReg.test(reg.email.trim()))) {
            alert("邮箱格式不正确");
            return;
        }

        if (reg.username == null) {
            alert("请填写用户名");
            return ;
        }

        if (!checkPassword(reg)) {
            alert("密码复杂度不够");
            return ;
        }

        if( reg.password != reg.password1) {
            alert("两次输入密码不一致");
            return ;
        }

        if (reg.verfiyCode == undefined || reg.verfiyCode == "" ) {
            alert("验证码不能为空");
            return ;
        }


        $http.post('/admin/register',reg)
            .then(function (response) {
                var status = response.data.data.status;
                var msg = response.data.data.message;

                if (status == 200){
                	alert("注册成功");
                    //$window.location.href = "auditing.html";
                } else if (status == 201) {
                    alert(msg);
                }

            })

    }


    $scope.timing = 0;


    //邮箱请求验证码
    $scope.verifyCode = function (reg) {

        //还没进行验证,先填数据
    	var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
        if (!(emailReg.test(reg.email.trim()))) {
            alert("邮箱格式不正确");
            return;
        }

        $scope.timing = 60;

        $scope.timeMachine();

        $http.post('/admin/verifyCode',reg)
            .then(function (response) {
                var status = response.data.data.status;
                var msg = response.data.data.message;

                if (status == 200){
                    console.log(msg);
                } else if(status == 201){
                    alert(msg);
                }

            })
    }


    //验证密码
    function checkPassword(reg) {

        var strength = 0;
        var value = reg.password;
        if (value.length > 7 && value.match(/[\da-zA-Z]+/)) {
            if (value.match(/\d+/)) {
                strength++;
            }
            if (value.match(/[a-z]+/)) {
                strength++;
            }
            if (value.match(/[A-Z]+/)) {
                strength++;
            }
            if (value.match(/[!@*$-_()+=&￥]+/)) {
                strength++;
            }
        }

        if (strength >= 2) {
            return true;
        }
        return false;
    }


    //计时器
    $scope.timeMachine = function () {

        if ($scope.timing != 0) {
            $timeout(function () {
                $scope.timing--;
                $scope.timeMachine();
            },1000);
        }

    }

}