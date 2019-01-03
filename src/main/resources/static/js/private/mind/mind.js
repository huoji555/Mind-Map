var mind = angular.module('mind',['ngRoute']);
var jm = null;          //JSMind options
var mapid = null;       //The MapId
var mapUser = null;     //The MapUser
var admin = null;       //The loginUser
var type = 0;           //check the browser fresh status

mind.config(['$routeProvider',function ($routeProvider) {
    $routeProvider.when('/',{templateUrl:"html/mind/myMapContent.html",controller:myMapController})
                  .when('/myMap',{templateUrl:"html/mind/myMapContent.html",controller:myMapController})
                  .when('/shareMap',{templateUrl:"html/mind/shareMapContent.html",controller:shareMapController})
                  .when('/openMap',{templateUrl:"html/mind/mindContent.html",controller:openMapController})
}]);



/*------------- 整体页面控制器 -------------*/
mind.controller('mindControl',function ($scope,$http,$window,$rootScope) {

    //验证是否登录
    $rootScope.ifLogin = function () {

        $http.post("/login/ifLogin")
            .then(function (response) {
                var status = response.data.data.status;
                var adminId = response.data.data.adminId;

                if (status == 201){$window.location = "login.html";
                } else if (status == 200){ mapUser = adminId;}

            })
    }
    $rootScope.ifLogin();

    //初始化JsMind
    $rootScope.open_empty = function () {
        var mind = {'data':{'id':'20180725213742','topic':'x','color':'浅紫','children':[{'id':'2018625213717','topic':'y','color':'天蓝'},{'id':'2018625213713','topic':'z','color':'郁金色'}]},'meta':{'author':'hizzgdev@163.com','name':'jsMindremote','version':'0.2'},'format':'node_tree'};
        var options = {
            container : 'jsmind_container',
            theme : 'greensea',
            editable : true
        }
        jm = new jsMind(options);
        jm.show(mind);
    }

    //新建图谱
    $rootScope.newMap = function () {

        $.messager.prompt('新建图谱','请输入图谱名称',function (r) {
            if (r) {
                type += 1;
                $window.location = "mindmap.html#!openMap";

                $http.post('/mindmap/newMap?nodeName='+r).then(function (response) {

                    var status = response.data.data.status;

                    if (status == 200) {
                        var datas = eval('('+ response.data.data.datas +')');
                        mapid = response.data.data.mapid;
                        jm.show(datas);
                    } else if (status == 201) {
                        $.messager.alert("操作提示", response.data.data.message, "info");
                    }

                })
            }
        })
    }

    //分享图谱
    $rootScope.shareMap = function (x) {

        if (x == 'outside') {x = mapid;}

        $http.post('/shareMap/share?mapid='+x).then(function (response) {

            var status = response.data.data.status;
            var msg = response.data.data.message;

            if (status == 200) {
                $.messager.alert("操作提示", "分享成功！", "info");
            } else if (status == 400) {
                $.messager.alert("操作提示", msg, "info");
            }

        })

    }

    //保存文件
    $rootScope.save_file = function () {
        var mind_data = jm.get_data();
        var mind_name = mind_data.meta.name;
        var mind_str = jsMind.util.json.json2string(mind_data);
        jsMind.util.file.save(mind_str, 'text/jsmind', mind_name + '.jm');
    }

    //打开文件
    $rootScope.open_file = function () {
        var file_input = document.getElementById('file_input');
        var files = file_input.files;
        if (files.length > 0) {
            var file_data = files[0];
            jsMind.util.file.read(file_data, function(jsmind_data,
                                                      jsmind_name) {
                var mind = jsMind.util.json.string2json(jsmind_data);
                if (!!mind) {
                    type += 1;
                    $window.location = "mindmap.html#!openMap";
                    jm.show(mind);
                } else {
                    alert('can not open this file as mindmap');
                }
            });
        } else {
            alert('please choose a file first');
        }
    }

    //打开文件触发
    $rootScope.open_browser = function() {
        $("#file_input").click();
    }

    //生成图片
    $rootScope.screen_shot = function () {
        jm.shoot();
    }
    
    //编辑颜色
    $rootScope.set_color = function (color) {
        var sel=$("jmnode.selected").attr("nodeid");
        var color1=shiftcolor(color);
        jm.set_node_color(sel, color1);

        $http.get('/mindmap/setColor',{params:{nodeid:sel,mapid:mapid,color:color1}}).then(function (response) {
            var status = response.data.data.status;
            var msg = response.data.data.message;

            if (status == 200) {console.log(msg);}
            else if (status == 400) {console.log(msg);}

        })
        
    }

    //颜色转换
    function shiftcolor(color){
        switch(color){
            case "浅紫":
                color="#CD96CD";
                break;
            case "郁金色":
                color="#fdb933";
                break;
            case "抹茶":
                color="#6BB073";
                break;
            case "咖色":
                color="#BF7F50";
                break;
            case "玫瑰红":
                color="#FF0000";
                break;
            case "原色":
                color="#1abc9c";
                break;
            case "圣诞红":
                color="#BF0A10";
                break;
            case "深紫":
                color="#9b59b6";
                break;
            case "藏青":
                color="#34495e";
                break;
            case "要什么颜色":
                color="#733C80";
                break;
            case "天蓝":
                color="#426ab3";
                break;
            case "砖红":
                color="#e74c3c";
                break;
            case "碳灰":
                color="#404040";
                break;
            case "亮粉":
                color="#ff3399";
                break;
            case "凑数色":
                color="#8B1A1A";
                break;
        }
        return color;
    }

    //遍历一个图谱的所有节点（加载图谱时调用）
    $rootScope.traverse = function (json) {

        if(!json){return ;}
        jm.set_node_color(json.id, json.color);
        if(json.children&&json.children.length>0){
            for(var i=0;i<json.children.length;i++){
                this.traverse(json.children[i]);
            }
        }
    }


});


/*------------- 加载我的图谱(list) ---------*/
function myMapController($scope,$http,$window,$rootScope) {

    var pageSize = 12;

    //分页部分
    $scope.pagenation = function (page,pageSzie) {

        $http.get('mindmap/getMyMap?page='+page+'&size='+pageSzie)
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

    //打开我的图谱
    $scope.openMyMap = function (x) {

        mapid = x;
        type += 1;
        $window.location = "mindmap.html#!openMap";
        $rootScope.openMap();

    }

    //删除我的图谱
    $scope.delMyMap = function (x) {

        $http.post('/mindmap/deleteNode?nodeid='+ x +'&mapid='+ x).then(function (response) {

            var status = response.data.data.status;
            var msg = response.data.data.message;

            if (status == 200) {
                if ($scope.lists.length == 1 && $scope.currPage != 0) {
                    $scope.currPage -= 1;
                }
                $scope.pagenation($scope.currPage,pageSize);
            } else if (status == 201) {
                $.messager.alert("操作提示", msg, "info");
            }

        })

    }

    //显示完整图谱
    $rootScope.openMap = function () {

        $http.post('/mindmap/openMap?mapid='+mapid).then(function (response) {

            var status = response.data.data.status;

            if (status == 200) {
                var datas = eval('('+ response.data.data.datas +')');
                mapid = response.data.data.mapid;
                mapUser = response.data.data.mapUser;
                jm.show(datas);
                $rootScope.traverse(datas.data);
            } else {
                $.messager.alert("操作提示", "服务器异常", "info");
            }

        })

    }

}


/*------------- 加载分享图谱（list） -------*/
function shareMapController($scope,$http,$window,$rootScope) {

    var pageSize = 12;

    //分页部分
    $scope.pagenation = function (page,pageSzie) {

        $http.get('shareMap/getShareMap?page='+page+'&size='+pageSzie)
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

    //删除分享图谱
    $scope.deleteShareMap = function (x,mindUser) {

        $http.get('/shareMap/delete?mapid='+x+'&mindUser='+mindUser).then(function (response) {

            var status = response.data.data.status;
            var msg = response.data.data.message;

            if (status == 200) {
                if ($scope.lists.length == 1 && $scope.currPage != 0) {
                    $scope.currPage -= 1;
                }
                $scope.pagenation($scope.currPage,pageSize);
            } else if (status == 400) {
                $.messager.alert("操作提示", msg, "info");
            }

        })

    }

    //打开我的图谱
    $scope.openMyMap = function (x) {

        mapid = x;
        type += 1;
        $window.location = "mindmap.html#!openMap";
        $rootScope.openMap();

    }

}


/*------------- 打开知识图谱 ---------------*/
function openMapController($scope,$http,$window,$rootScope) {

    $rootScope.open_empty();

    //刷新浏览器状态(刷新状态为0，不显示数据，跳回列表界面)
    function checkFreshStatus() {
        if (type == 0) {
            $window.location = "mindmap.html#!myMap";
        }
    }
    checkFreshStatus();

    //右键菜单---新建子标签
    $rootScope.addNode = function () {

        $.messager.defaults = {ok : "是", cancel : "否"};

        $.messager.prompt('新增子标签','请输入子标签名称',function (options) {

            var r = loap(options);
            if (r) {
                var nodeid = jsMind.util.uuid2.newid();
                var topic = r;
                var selectId = get_selected_nodeid();

                if (mapUser == admin) {var node = jm.add_node(jm.get_selected_node(), nodeid, topic, null,"right");}

                $http.post('/mindmap/addNode?nodeid='+nodeid+'&topic='+topic+'&parentid='+selectId+'&mapid='+mapid)
                    .then(function (response) {

                        var status = response.data.data.status;
                        var msg = response.data.data.message;

                        if (status == 200) {
                            console.log(msg);
                        } else if (status == 201) {
                            console.log(msg);
                        } else if (status == 400) {
                            $.messager.alert("操作提示", msg, "info");
                        }

                    })

            }
        });


    }

    //右键菜单---修改子标签
    $rootScope.modifyNode = function () {

        var selected_id = get_selected_nodeid();
        var selected_name = get_selected_nodeName();
        $.messager.defaults = {ok : "是", cancel : "否"};

        $.messager.prompt('编辑标签','请输入新的标签名',function (options) {

            var r = loap(options);

            if (r) {
                if (mapUser == admin) {jm.update_node(selected_id,r);}

                $http.post('/mindmap/modifyNode?nodeid='+selected_id+'&nodename='+r+'&mapid='+mapid).
                then(function (response) {

                    var status = response.data.data.status;
                    var msg = response.data.data.message;

                    if (status == 200) {
                        console.log(msg);
                    } else if (status == 201) {
                        console.log(msg);
                    } else if (status == 400) {
                        $.messager.alert("操作提示", msg, "info");
                    }

                });
            }
        },""+selected_name+"");

    }

    //右键菜单---删除节点
    $rootScope.deleteNode = function () {

        $.messager.defaults = {ok : "是", cancel : "否"};

        $.messager.confirm("操作提示", "您确定要执行操作吗？", function(r) {

            if (r) {
                var selected_node = jm.get_selected_node();
                var selected_id = get_selected_nodeid();

                if( (!selected_ifRoot()) && (mapUser == admin) ){jm.remove_node(selected_node);}

                $http.post('/mindmap/deleteNode?nodeid='+selected_id+'&mapid='+mapid).then(function (response) {

                    var status = response.data.data.status;
                    var msg = response.data.data.message;

                    if (status == 200) {
                        console.log(msg);
                        if (selected_ifRoot() && (mapUser == admin) ) {
                            //fresh page
                            $window.location = "mindmap.html#!myMap";
                        }
                    } else if (status == 201) {
                        console.log(msg);
                    }  else if (status == 400) {
                        $.messager.alert("操作提示", msg, "info");
                    }

                })

            }

        });

    }

    //右键菜单---显示子节点
    $rootScope.showChild = function () {

        var selected_id = get_selected_nodeid();

        $http.post('/mindmap/getChildMap?nodeid='+selected_id+'&mapid='+mapid).then(function (response) {

            var status = response.data.data.status;

            if (status == 200) {
                var datas = eval('('+ response.data.data.datas +')');
                jm.show(datas);
            } else {
                $.messager.alert("操作提示", "服务器异常", "info");
            }

        })

    }


    //特殊字符转义(解决建立新标签的问题)
    function loap(options){
        var str = options;
        var str1 = str.replace(/&/g,"&amp;");
        var str2 = str1.replace(/>/g,"&gt;");
        var str3 = str2.replace(/</g,"&lt;");
        return str3;
    }

    //获得选中节点的nodeid
    function get_selected_nodeid() {
        var selected_node = jm.get_selected_node();
        if (!!selected_node) {   //两个叹号是为了将之数据类型转换为布朗型的
            return selected_node.id;
        } else {
            return null;
        }
    }

    //获得选中的节点
    function get_selected_node(){
        var select_node=jm.get_selected_node();
        if(!!select_node){
            return selected_node;
        }else{
            return null;
        }
    }

    //获得选中的节点名称
    function get_selected_nodeName(){
        var selected_node = jm.get_selected_node();
        if (!!selected_node) {   //两个叹号是为了将之数据类型转换为布朗型的
            return selected_node.topic;
        } else {
            return null;
        }
    }

    //判断选中的节点是否为根节点
    function selected_ifRoot(){
        var selected_node = jm.get_selected_node();
        if (!!selected_node) {   //两个叹号是为了将之数据类型转换为布朗型的
            return selected_node.isroot;
        } else {
            return null;
        }
    }

}










