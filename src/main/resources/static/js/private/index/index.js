var indexApp = angular.module('index',['ngRoute']);

indexApp.config(['$routeProvider',function ($routeProvider) {
    $routeProvider.when('/',{templateUrl:"html/Index/indexContent.html",controller:indexController})
                  .when('/adminMessage',{templateUrl:"html/manger/adminContent.html",controller:adminMessageController})
                  .when('/loginRecord',{templateUrl:"html/manger/loginRecordContent.html",controller:loginRecordController})
}]);




/*---------------------------------------首页controller--------------------------------*/
indexApp.controller('indexCon',function ($scope,$http,$window,$rootScope) {

    //退出登录
    $rootScope.quit = function () {

        $http.post("/login/logOut")
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

        $http.post("/login/ifLogin")
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
                $scope.adminId = adminId;
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

        $http.get('/login/updatePwd?orignalPwd='+$scope.orignPwd+'&newPwd='+$scope.repPWD).then(function (response) {

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

});

function indexController($scope,$http,$window,$rootScope) {

}




/*-------------------------  用户信息查看  ------------------------------*/
function adminMessageController($scope,$http,$window,$rootScope,$filter) {

    var pageSize = 10;
    /*获取用户信息(分页，默认查询所有信息)*/
    $scope.getMessage = function (page,pageSize) {

        var firstDate = $filter('date')($("#firstDate").val(),"yyyy-MM-dd");
        var lastDate = $filter('date')($("#lastDate").val(), "yyyy-MM-dd");

        $http.post('admin/queryAdminByDate?firstDate1='+firstDate+'&lastDate1='+lastDate+'&page='+page+'&size='+pageSize)
            .then(function (response) {
                $scope.totalNum = response.data.data.totalElements;//数据总数
                $scope.pages = response.data.data.totalPages;//页数
                $scope.currPage = response.data.data.number;//当前页
                $scope.isFirstPage = response.data.data.first;//是否是首页
                $scope.isLastPage = response.data.data.last;//是否是尾页
                $scope.lastUpPage = $scope.pages - 1;//倒数第二页
                $scope.lists = response.data.data.content;
            })

    }

    $scope.getMessage(0,pageSize);

    /*分页下部*/
    $scope.page = function (page,oper) {

        if(oper == 'first'){ //首页
            $scope.getMessage(0,pageSize)
        }
        if(oper == 'up'){   //上一页
            if (page == 0){
                return;
            }
            $scope.getMessage(page-1,pageSize);
        }
        if(oper == 'next'){ //下一页
            if (page == $scope.pages-1){
                return;
            }
            $scope.getMessage(page+1,pageSize);
        }
        if (oper == 'last'){  //末页
            $scope.getMessage(page-1,pageSize);
        }
    }

    /*模态框获取信息*/
    $scope.getAuthorizeInfo = function (username,roleId) {
        $scope.AutUsername = username;
        $scope.AutRoleId = roleId;
    }

    /*系统授权*/
    $scope.saveAut = function () {

        var autType = $("#autType").val();

        if (autType == 0) {
            alert("请选择授权类型");
            return ;
        }

        $http.post('/admin/updateAuthorize?username='+$scope.AutUsername+'&newRoleId='+autType)
            .then(function (response) {

                var status = response.data.data.status;
                var msg = response.data.data.message;

                if( status == 200 ) {
                    $scope.getMessage($scope.currPage,pageSize);
                    $scope.AutRoleId = autType;
                    alert(msg);
                }

            })

    }

}





/*-------------------------- 登录信息查看 -------------------------------*/
function loginRecordController($scope,$http,$window,$rootScope,$filter) {

    var pageSize = 20;

    /*分页信息*/
    $scope.pagenation = function (page,pageSize) {

        var firstDate = $filter('date')($("#firstDate").val(),"yyyy-MM-dd");
        var lastDate = $filter('date')($("#lastDate").val(), "yyyy-MM-dd");

        $http.post('admin/queryLoginRecordByDate?firstDate1='+firstDate+'&lastDate1='+lastDate+'&page='+page+'&size='+pageSize)
            .then(function (response) {
                $scope.totalNum = response.data.data.totalElements;//数据总数
                $scope.pages = response.data.data.totalPages;//页数
                $scope.currPage = response.data.data.number;//当前页
                $scope.isFirstPage = response.data.data.first;//是否是首页
                $scope.isLastPage = response.data.data.last;//是否是尾页
                $scope.lastUpPage = $scope.pages - 1;//倒数第二页
                $scope.lists = response.data.data.content;
            })

    }

    $scope.pagenation(0,pageSize);

    /*分页下部*/
    $scope.page = function (page,oper) {

        if(oper == 'first'){ //首页
            $scope.pagenation(0,pageSize)
        }
        if(oper == 'up'){   //上一页
            if (page == 0){
                return;
            }
            $scope.pagenation(page-1,pageSize);
        }
        if(oper == 'next'){ //下一页
            if (page == $scope.pages-1){
                return;
            }
            $scope.pagenation(page+1,pageSize);
        }
        if (oper == 'last'){  //末页
            $scope.pagenation(page-1,pageSize);
        }
    }

}




