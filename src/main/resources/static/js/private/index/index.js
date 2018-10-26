var indexApp = angular.module('index',['ngRoute']);

indexApp.config(['$routeProvider',function ($routeProvider) {
    $routeProvider.when('/',{templateUrl:"html/Index/indexContent.html",controller:indexController})
}]);




/*---------------------------------------首页controller--------------------------------*/
indexApp.controller('indexCon',function ($scope,$http,$window,$rootScope) {

    //退出登录
    $rootScope.quit = function () {

        $http.post("/admin/logOut")
            .then(function (response) {
                var status = response.data.data.status;
                var msg = response.data.data.message;

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
                var status = response.data.data.status;
                var msg = response.data.data.message;
                var roleId = response.data.data.roleId;
                var adminId = response.data.data.adminId;

                if (status == 201){
                    alert("您还未登录，请登录后再操作");
                    $window.location = "login.html";
                }
                $scope.roleId = roleId;
                textToImg(adminId);
            })
    }

    $rootScope.ifLogin();
    $rootScope.first = '系统介绍';

    /*动态赋值*/
    $rootScope.changeName = function (first,second) {
        $rootScope.first = first;
    }

    /*修改密码*/
    $scope.update = function () {

        if( $scope.newPwd != $scope.repPWD) {alert("两次输入密码不一致");return ;}

        if (!checkPassword($scope.repPWD)) {alert("请尝试更复杂的密码");return ;}

        $http.get('/admin/updatePwd?orignalPwd='+$scope.orignPwd+'&newPwd='+$scope.repPWD).then(function (response) {

            var status = response.data.data.status;
            var msg = response.data.data.message;

            if (status == 200){
                alert(msg);
            } else if (status == 201){
                alert(msg);
            }
        });

    }

    //验证密码
    function checkPassword(pwd) {

        var strength = 0;
        var value = pwd;
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

    //生成头像图片
    function textToImg(text){
        var ucompany = text;
        var company = ucompany.charAt(0);
        var fontSize = 60;
        var fontWeight = 'bold';

        var canvas = document.getElementById('headImg');
        var img1 = document.getElementById('headImg');
        canvas.width = 120;
        canvas.height = 120;
        var context = canvas.getContext('2d');
        context.fillStyle = '#F7F7F9';
        context.fillRect(0, 0, canvas.width, canvas.height);
        context.fillStyle = '#605CA8';
        context.font = fontWeight + ' ' + fontSize + 'px sans-serif';
        context.textAlign = 'center';
        context.textBaseline="middle";
        context.fillText(company, fontSize, fontSize);
        $('.img-avatar').attr('src',canvas.toDataURL("image/png"));
    };

    //判断浏览器是不是IE
    function checkExp(){
        if ( navigator.userAgent.match(/rv:(\d+)\..*/) ){
            return "IE";
        } else {
            return "NotIE";
        }
    }

    //提示IE用户
    function ieMsg() {
        if (checkExp() == "IE") {
            alert("不支持IE浏览器下载，请尝试别的浏览器");
            return;
        }
    }

    ieMsg();

});

function indexController($scope,$http,$window,$rootScope) {

}




/*-------------------------  用户信息查看  ------------------------------*/
function adminMessageController($scope,$http,$window,$rootScope) {

    /*获取用户信息*/
    $scope.getMessage = function () {

        $http.get('auditing/getAdminMessage')
            .then(function (response) {
                $scope.list = response.data.data;
            })

    }


    $scope.getMessage();


    /*显示用户信息*/
    $scope.showMessage = function (url,type) {
        $scope.message = url;
    }

    /*下载文件*/
    $scope.download = function (url) {
        $window.location = url;
    }

}




