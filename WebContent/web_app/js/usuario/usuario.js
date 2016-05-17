var app = angular.module('fatec');

app.controller('UsuarioController', function($scope, $http, $timeout) {

	var urlPath = "http://localhost:8585/projeto_exemplo/Usuario!";
	TelaHelper.tela = 'usuario';
	$scope.usuarios = [];
	$scope.currentPage = 1;
	$scope.itemsPerPage = 5
	$scope.usuario = {};
	$scope.grupos = [];

	$scope.loadPapeis = function() {
		$http.get(urlPath + 'listar.action', {
			cache : false
		}).success(function(response) {
	    	carregarLista(response);
		});
	};

	$scope.addGrupo = function() {
		if ($scope.usuario.grupos == null) {
			$scope.usuario.grupos = [];
		}
		$scope.usuario.grupos.push({});
	}

	$scope.addPapel = function() {
		if ($scope.usuario.papeis == null) {
			$scope.usuario.papeis = [];
		}
		$scope.usuario.papeis.push({});
	}
	
	$scope.removeGrupo = function(index) {
		$scope.usuario.grupos = $scope.usuario.grupos.slice(1, index);
	}

	$scope.removePapel = function(index) {
		$scope.usuario.papeis = $scope.usuario.papeis.slice(1, index);
	}
	
	$scope.salvar = function() {
		var data = {contexto : {
			usuario : $scope.usuario
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
			usuario : {id : id}
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
				usuario : {id : id}
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
			        $scope.usuario = response.contexto.usuario;
			    }
			});
		}
		jQuery.ajax({
		    url: urlPath + 'listarGruposEPapeis.action',
		    data: '{"contexto":{}}',
		    dataType: 'json',
		    contentType: 'application/json',
		    type: 'POST',
		    async: false,
		    success: function (response) {
		        $scope.grupos = response.contexto.grupos;
		        $scope.papeis = response.contexto.papeis;
		    }
		});
		jQuery('#modalForm').modal('show');
	};

	$scope.cancelarModal = function() {
		$scope.usuario = {};
		fecharModal();
	};

	function carregarLista(response) {
		$scope.usuarios = response.contexto.usuarios;
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