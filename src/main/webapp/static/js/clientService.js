var myapp = angular.module('ui.bootstrap.demo', ['ngAnimate', 'ui.bootstrap']);

myapp.controller('SignupCtrl', function ($scope, $http) {
	
	$scope.inputName = null;
	$scope.inputPW = null;
	$scope.inputEmail = null;
	$scope.checkAndSignup = function(name, email, pw, pwc){
		if (pw == pwc)
		{
			if(name =="" || email == "" || pw == "")
			{
				alert("Insufficient Data! ");
			}
			else
			{
				$scope.url = 'http://localhost:8080/mydrive/api/createmember/'+name+'/'+email+'/'+pw
				$http.post($scope.url)
					.success(function (response) {
				    	if(response.result =='good')
				    	{
				    		sessionStorage.user = JSON.stringify(name);
				    		window.location.replace("http://localhost:8080/mydrive/upload/");
				    	}
				    	});
			}
		}

	}
	
	});



myapp.controller('loginCtrl', function ($scope, $http, $location) {
	$scope.warningEnabled = false;
	sessionStorage.user = JSON.stringify("Default");
	$scope.redirectToRegister=function(){
		window.location.replace("http://localhost:8080/mydrive/signup");
	}
	$scope.checkCredential = function(name, pw){
		if(name == null || pw == null )
		{
			$scope.warningEnabled = true;
		}
		else
		{
			$scope.url = 'http://localhost:8080/mydrive/api/login/'+name+'/'+pw;
			$http.get($scope.url)
			    .success(function (response) {
			    	if(response.statusCode =='200')
			    	{ 
			    		sessionStorage.user = JSON.stringify(name);	
			    		window.location.replace("http://localhost:8080/mydrive/upload");	    		
			    	}
			    	else{
			    		$scope.warningEnabled = true;
			    	}
			    	});
		}
	}
	});

myapp.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

myapp.service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl){
        var fd = new FormData();
        fd.append('file', file);
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(){
        })
        .error(function(){
        });
    }
}]);


myapp.controller('myCtrl', ['$scope', '$http', 'fileUpload', function($scope, $http, fileUpload){
	$scope.init = function () {
	   console.log("Page loaded!!");
	  $scope.userName = JSON.parse(sessionStorage.user);
	  if( $scope.userName ==='Default'){
		  window.location.replace("http://localhost:8080/mydrive/login");
	  }
	  //$scope.urlGetFileList = "http://localhost:8080/mydrive/api/getFileListById/kevin@gmail.com/";
	   $scope.urlGetFileList = "http://localhost:8080/mydrive/api/getFileListById/"+$scope.userName+"/";
		$http.post($scope.urlGetFileList)
		    .success(function (response) {$scope.FileList = response;});
	};
	$scope.signOut = function(){
		sessionStorage.user = JSON.stringify("Default");
		window.location.replace("http://localhost:8080/mydrive/login");
	};
	
	
	$scope.download = function (file) {
	     console.log("download the file:"+file.fileName);
	     var downloadUrl = 'http://localhost:8080/mydrive/download/'+file.fileName+'/';
	     window.open(downloadUrl, '_blank', '');  
	};
	
	$scope.deleteFile = function(file){
		console.log("delete the file:"+file.fileName);
		var userName = $scope.userName;
		var fileName = file.fileName;
		$scope.url = 'http://localhost:8080/mydrive/api/deleteFile/'+userName+'/'+fileName+'/';
		$http.post($scope.url)
		    .success(function (response) {
		    	if(response.statusCode =='200')
		    	{ 		
		    		window.location.replace("http://localhost:8080/mydrive/upload");	    		
		    	}
		    	});
	}
	
    $scope.uploadFile = function(){
        var file = $scope.myFile;
        var userName = $scope.userName;
        console.log('file is ' );
        console.dir(file);
        var uploadUrl = "/mydrive/singleUpload/"+userName+"/";
        fileUpload.uploadFileToUrl(file, uploadUrl);
        window.location.reload();
    };
    
}]);



