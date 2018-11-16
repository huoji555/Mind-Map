var mind = angular.module('mind',['ngRoute']);

mind.config(['$routeProvider',function ($routeProvider) {
    //$routeProvider.when('/',{templateUrl:"html/mind/mindContent.html",controller:MindController})
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
        jm.show(mind);
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
    $scope.addNode  = function () {
        
        $.messager.defaults = {
            ok : "是",
            cancel : "否"
        };
        
        $.messager.prompt('新增子标签','请输入子标签名称',function (options) {

            var r = loap(options);
            if (r) {
                var nodeid = jsMind.util.uuid2.newid();
                var topic = r;
                var selectId = get_selected_nodeid();

                var node = jm.add_node(jm.get_selected_node(), nodeid, topic, null,"right");
                alert(topic);

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


});


function MindController($scope,$http,$window,$rootScope) {

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