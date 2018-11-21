var mind = angular.module('mind',['ngRoute']);

mind.config(['$routeProvider',function ($routeProvider) {
    $routeProvider.when('/',{templateUrl:"html/mind/myMapContent.html",controller:myMapController})
}]);


mind.controller('mindControl',function ($scope,$http,$window,$rootScope) {

    var jm = null;
    var mapid = null;

    //加载知识图谱引用
    $scope.open_empty = function () {
        var mind = {'data':{'id':'20180725213742','topic':'秋宁','color':'浅紫','children':[{'id':'2018625213717','topic':'可爱','color':'天蓝'},{'id':'2018625213713','topic':'美','color':'郁金色'}]},'meta':{'author':'hizzgdev@163.com','name':'jsMindremote','version':'0.2'},'format':'node_tree'};
        var options = {
            container : 'jsmind_container',
            theme : 'greensea',
            editable : true
        }
        jm = new jsMind(options);
        //jm.show(mind);
    }
    $scope.open_empty();

    //新建图谱
    $scope.newMap = function () {

        $.messager.prompt('新建图谱','请输入图谱名称',function (r) {
            if (r) {

                $http.post('/mindmap/newMap?nodeName='+r).then(function (response) {

                    var status = response.data.data.status;

                    if (status == 200) {
                        var datas = eval('('+ response.data.data.datas +')');
                        mapid = response.data.data.mapid;
                        jm.show(datas);
                    } else if (status == 201) {
                        alert(response.data.data.message);
                    }

                })
            }
        })
    }

    //右键菜单---新建子标签
    $scope.addNode = function () {
        
        $.messager.defaults = {ok : "是", cancel : "否"};
        
        $.messager.prompt('新增子标签','请输入子标签名称',function (options) {

            var r = loap(options);
            if (r) {
                var nodeid = jsMind.util.uuid2.newid();
                var topic = r;
                var selectId = get_selected_nodeid();

                var node = jm.add_node(jm.get_selected_node(), nodeid, topic, null,"right");

                $http.post('/mindmap/addNode?nodeid='+nodeid+'&topic='+topic+'&parentid='+selectId+'&mapid='+mapid)
                    .then(function (response) {

                        var status = response.data.data.status;
                        var msg = response.data.data.message;

                        if (status == 200) {
                            console.log(msg);
                        } else if (status == 201) {
                            alert(msg);
                        }
                        
                    })

            }
        });

        
    }

    //右键菜单---修改子标签
    $scope.modifyNode = function () {

        var selected_id = get_selected_nodeid();
        var selected_name = get_selected_nodeName();
        $.messager.defaults = {ok : "是", cancel : "否"};

        $.messager.prompt('编辑标签','请输入新的标签名',function (options) {

            var r = loap(options);

            if (r) {
                jm.update_node(selected_id,r);

                $http.post('/mindmap/modifyNode?nodeid='+selected_id+'&nodename='+r+'&mapid='+mapid).
                    then(function (response) {

                        var status = response.data.data.status;
                        var msg = response.data.data.message;

                        if (status == 200) {
                            console.log(msg);
                        } else if (status == 201) {
                            alert(msg);
                        }

                    });
            }
        },""+selected_name+"");

    }
    
    //右键菜单---删除节点
    $scope.deleteNode = function () {

        $.messager.defaults = {ok : "是", cancel : "否"};

        $.messager.confirm("操作提示", "您确定要执行操作吗？", function(r) {

            if (r) {
                var selected_node = jm.get_selected_node();
                var selected_id = get_selected_nodeid();

                if(selected_ifRoot()){
                    //fresh page
                    $window.location.reload();
                } else {
                    jm.remove_node(selected_node);
                }

                $http.post('/mindmap/deleteNode?nodeid='+selected_id+'&mapid='+mapid).then(function (response) {

                    var status = response.data.data.status;
                    var msg = response.data.data.message;

                    if (status == 200) {
                        console.log(msg);
                    } else if (status == 201) {
                        alert(msg);
                    }

                })

            }

        });

    }

    //右键菜单---显示子节点
    $scope.showChild = function () {

        var selected_id = get_selected_nodeid();

        $http.post('/mindmap/getChildMap?nodeid='+selected_id+'&mapid='+mapid).then(function (response) {

            var status = response.data.data.status;

            if (status == 200) {
                var datas = eval('('+ response.data.data.datas +')');
                jm.show(datas);
            } else {
                alert("服务器异常");
            }

        })

    }

    //右键菜单---显示完整图谱
    $scope.openMap = function () {

        $http.post('/mindmap/openMap?mapid='+mapid).then(function (response) {

            var status = response.data.data.status;

            if (status == 200) {
                var datas = eval('('+ response.data.data.datas +')');
                mapid = response.data.data.mapid;
                jm.show(datas);
            } else {
                alert("服务器异常");
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

});


/*------------- 加载我的图谱 ---------------*/
function myMapController($scope,$http,$window,$rootScope) {

    var pageSize = 15;

    $("#jsmind_container").hide();

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


}


//右键菜单
$(function () {

    $("jmnodes").on('contextmenu', function(e) {
        e.preventDefault();
        $('#mm').menu('show', { //菜单EasyUI
            left : e.pageX,
            top : e.pageY,
            hideOnUnhover : false
        });
    });
});

