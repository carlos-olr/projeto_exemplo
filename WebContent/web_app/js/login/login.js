var app = angular.module('fatec');
var urlPath = "http://localhost:8585/projeto_exemplo/Login!";

app.controller('LoginController', ['$scope', '$http', '$timeout', '$cookies',
                                   function($scope, $http, $timeout, $cookies) {
	
	$scope.usuario = {};
	$scope.isLogado = false;
	
	$scope.doLogin = function() {
		var data = {contexto : {
			usuario : $scope.usuario
		}};
		
		var data1 = JSON.stringify(data);
		jQuery.ajax({
		    url: urlPath + 'doLogin.action',
		    data: data1,
		    dataType: 'json',
		    contentType: 'application/json',
		    type: 'POST',
		    async: false,
		    success: function (response) {
		    	if (response.contexto.usuario == null) {
		    		// mostrar msg de erro
		    	}
		    	StorageHelper.setItem('usuario', response.contexto.usuario);
		    	$scope.isLogado = true;
		    }
		});
	};
	
	$scope.doLogout	 = function() {
		
	};
	
}]);