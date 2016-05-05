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
				    		window.location.replace("http://localhost:8080/mydrive/upload/");
				    	}
				    	});
			}
		}

	}
	
	});



myapp.controller('loginCtrl', function ($scope, $http, $location) {
	
	$scope.redirectToRegister=function(){
		window.location.replace("http://localhost:8080/mydrive/signup");
	}
	$scope.checkCredential = function(email, pw){
		if(email == null || pw == null )
		{
			alert("Insufficient Data! ");
		}
		else
		{
			$scope.url = 'http://localhost:8080/mydrive/api/login/'+email+'/'+pw;
			$http.get($scope.url)
			    .success(function (response) {
			    	if(response.statusCode =='200')
			    	{    		
			    		window.location.replace("http://localhost:8080/mydrive/");
		
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

myapp.controller('myCtrl', ['$scope', 'fileUpload', function($scope, fileUpload){

    $scope.uploadFile = function(){
        var file = $scope.myFile;
        console.log('file is ' );
        console.dir(file);
        var uploadUrl = "/mydrive/singleUpload";
        fileUpload.uploadFileToUrl(file, uploadUrl);
    };
    
}]);
