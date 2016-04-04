var app = angular.module('fatec');

app.controller('GrupoController', function($scope, $http, $timeout) {

	var urlPath = "http://localhost:8585/projeto_exemplo/Grupo!";
	TelaHelper.tela = 'grupo';
	$scope.grupos = [];
	$scope.currentPage = 1;
	$scope.itemsPerPage = 5
	$scope.grupo = {};

	$scope.loadGrupos = function() {
		$http.get(urlPath + 'listar.action', {
			cache : false
		}).success(function(response) {
	    	carregarLista(response);
		});
	};

	$scope.salvar = function() {
		var data = {contexto : {
			grupo : $scope.grupo
		}};
		
		var data1 = JSON.stringify(data);
		jQuery.ajax({
		    url: urlPath + 'salvar.action',
		    data: data1,
		    dataType: 'json',
		    contentType: 'application/json',
		    type: 'POST',
		    async: true,
		    success: function (response) {
		        $scope.cancelarModal();
		    	carregarLista(response);
		    }
		});
	};
	
	$scope.deletar = function(id) {
		var data = {contexto : {
			grupo : {id : id}
		}};
		
		var data1 = JSON.stringify(data);
		jQuery.ajax({
		    url: urlPath + 'deletar.action',
		    data: data1,
		    dataType: 'json',
		    contentType: 'application/json',
		    type: 'POST',
		    async: false,
		    success: function (response) {
		    	$scope.id = null;
		    	carregarLista(response);
		    }
		});
	}
	
	$scope.abrirModal = function(id) {
		if (id) {
			var data = {contexto : {
				grupo : {id : id}
			}};

			var data1 = JSON.stringify(data);
			jQuery.ajax({
			    url: urlPath + 'editar.action',
			    data: data1,
			    dataType: 'json',
			    contentType: 'application/json',
			    type: 'POST',
			    async: false,
			    success: function (response) {
			        $scope.grupo = response.contexto.grupo;
			    }
			});
		}
		jQuery('#modalForm').modal('show');
	};

	$scope.cancelarModal = function() {
		$scope.grupo = {};
		fecharModal();
	};

	function carregarLista(response) {
		$scope.grupos = response.contexto.grupos;
		$scope.currentPage = 1;
		$scope.$applyAsync();
	}
	
	function fecharModal() {
		jQuery('#modalForm').modal('hide');
	};
	
	setTimeout(function() {
		$scope.loadGrupos();
	}, 0);
	
});