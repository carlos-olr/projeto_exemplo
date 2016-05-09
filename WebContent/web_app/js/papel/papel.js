var app = angular.module('fatec');

app.controller('PapelController', function($scope, $http, $timeout) {

	var urlPath = "http://localhost:8585/projeto_exemplo/Papel!";
	TelaHelper.tela = 'papel';
	$scope.papeis = [];
	$scope.currentPage = 1;
	$scope.itemsPerPage = 5
	$scope.papel = {
			grupo : {
				id : null
			}
	};
	$scope.grupos = [];

	$scope.loadPapeis = function() {
		$http.get(urlPath + 'listar.action', {
			cache : false
		}).success(function(response) {
	    	carregarLista(response);
		});
	};

	$scope.salvar = function() {
		var data = {contexto : {
			papel : $scope.papel
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
			papel : {id : id}
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
			var data = {contexto : {}};
			data.contexto = {
				papel : {id : id}
			};

			var data1 = JSON.stringify(data);
			jQuery.ajax({
			    url: urlPath + 'editar.action',
			    data: data1,
			    dataType: 'json',
			    contentType: 'application/json',
			    type: 'POST',
			    async: false,
			    success: function (response) {
			        $scope.papel = response.contexto.papel;
			    }
			});
		}
		jQuery.ajax({
		    url: urlPath + 'listarGrupos.action',
		    data: '{"contexto":{}}',
		    dataType: 'json',
		    contentType: 'application/json',
		    type: 'POST',
		    async: false,
		    success: function (response) {
		        $scope.grupos = response.contexto.grupos;
		    }
		});
		jQuery('#modalForm').modal('show');
	};

	$scope.cancelarModal = function() {
		$scope.papel = {};
		fecharModal();
	};

	function carregarLista(response) {
		$scope.papeis = response.contexto.papeis;
		$scope.currentPage = 1;
		$scope.$applyAsync();
	}
	
	function fecharModal() {
		jQuery('#modalForm').modal('hide');
	};
	
	setTimeout(function() {
		$scope.loadPapeis();
	}, 0);
	
});