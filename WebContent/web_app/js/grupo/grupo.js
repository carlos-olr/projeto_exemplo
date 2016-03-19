var app = angular.module('fatec', ['ui.bootstrap']);
var urlPath = "http://localhost:8585/projeto_exemplo/Grupo!";

app.controller('grupoCtrl', function($scope, $http, $timeout) {

	$scope.grupos = [];
	$scope.currentPage = 1;
	$scope.itemsPerPage = 5

	$scope.loadGrupos = function() {
		$http.get(urlPath + 'listar.action', {
			cache : false
		}).success(function(response) {
			$scope.grupos = response.contexto.grupos;
			$scope.currentPage = 1;
		});
	};

	setTimeout(function() {
		$scope.loadGrupos();
	}, 0);

});