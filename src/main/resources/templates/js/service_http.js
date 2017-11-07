angular.module('myApp', []).
  controller('myController', ['$scope', '$http',
                              function($scope, $http) {

   //初始化数据
          $scope.name="";
          $scope.age="";
          $scope.hobby="";

               $http.get('/getall')
                .success(function(data, status, headers, config) {
                  $scope.days = data;
                   }).
                    error(function(data, status, headers, config) {
                       $scope.status = data;
               });

    $scope.menuState={show: false};

    $scope.add = function(){
        $scope.menuState.show=!$scope.menuState.show;
    }
    $scope.submit = function(name,age,hobby){
    	alert(name);
    	alert(age);
    	alert(hobby);
        $scope.menuState={show: false};
        $http.post('/add?name='+name+"&age="+age+"&hobby="+hobby)
        .success(function(data,status,headers,config){
            $scope.days =data;
        }).error(function(data,status,headers,config){
            alert("服务器错误");
            $scope.status =data.msg;
        })
    }

    $scope.days=[];
    $scope.status = "";
    $scope.removeDay = function(deleteDay){

      $http.post('delete?id='+deleteDay).
        success(function(data, status, headers, config) {
          $scope.days = data;
        }).
        error(function(data, status, headers, config) {
          $scope.status = data.msg;
        });
    };
    $scope.resetDays = function(){
      $scope.status = "";
      $http.get('/reset/days')
               .success(function(data, status, headers, config) {
        $scope.days = data;
      }).
      error(function(data, status, headers, config) {
        $scope.status = data;
      });
    };
  }]);