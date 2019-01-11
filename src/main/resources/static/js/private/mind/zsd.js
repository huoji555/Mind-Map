var zsdBefore = null;
var zsd = angular.module('zsd',['ngRoute']);
zsd.config(['$routeProvider',function ($routeProvider) {
    $routeProvider.when('/',{templateUrl:"html/mind/zsdContent.html",controller:zsdController})
}]);


function zsdController($scope,$http,$window,$rootScope) {

    $scope.saveZsd = function () {

        var nodeid = parent.get_selected_nodeid();
        var mapid  = parent.get_mapid();
        var describe = $("#a_content").summernote('code').trim();

        if (describe == '请添加知识点描述' || describe == "") {
            //del the zsd
            if ( describe != zsdBefore ) {
                $http.get('zsd/delZsd',{params:{nodeid:nodeid}})
                return;
            } else {
                alert("请重新输入");
                return;
            }
        }

        if (describe == zsdBefore) {return;}

        var data = JSON.stringify({"nodeid":nodeid,"zsdms":describe,"mapid":mapid});

        $http.post('zsd/save',data).then(function (response) {
            var status = response.data.data.status;
            var msg = response.data.data.message;

            if (status == 200) {
                zsdBefore = describe;
                alert(msg);
            } else if (status == 400) {
                alert(msg);
            }

        })

    }

    $scope.getZsd = function () {
        var nodeid = parent.get_selected_nodeid();
        var html = null;
        $scope.zsdmc = parent.get_selected_nodeName();
        $('#a_content').summernote({
            height: 400,
            tabsize: 2,
            minHeight: 20,
            maxHeight: 900,
            lang: 'zh-CN'
        });            /* 加载模态框配置 */

        $http.get('zsd/getZsd',{params:{nodeid:nodeid}}).then(function (response) {
            var status = response.data.data.status;
            var data = response.data.data.data;

            if (status == 200 || status == 400) {
                $('.summernote').summernote('code',data);
                zsdBefore = data;
            }

        })

    }

    $scope.getZsd();

    /*$scope.mmp = function ($event) {
        if($event.ctrlKey == true && $event.keyCode == 83){
            alert("jibai");
            event.returnValue=false;
        }
    }*/

}