// Para criarmos um controlador precisamos definir a qual módulo ele fará parte.
// Executar 'angular.module('NOME_MODULO')' sem passar um array é forma de fazer GET
// do módulo.
var app = angular.module('fatec');

// Para criarmos um Controlador executamos a função 'controller' a partir do módulo
// Um controller pode possuir depedências, que são outros controllers ou serviços angular
// o padrão de um controller é:
// app.controller('NOME_CONTROLLER', ['$scope', function($scope) {}]);
app.controller('LoginController', ['$scope', '$http', '$timeout', '$sce',
                                   function($scope, $http, $timeout, $sce) {

	// por padrão defininos variáveis contantes no início do arquivo
	var CHAVE_STORAGE = 'usuario';
	var urlPath = "http://localhost:8585/projeto_exemplo/Login!";

	// O $scope é um objeto público que permite o HTML ter acesso ao Controller,
	// tudo que é declarado no scope pode ser usado na tela.
	// representa o usuário logado
	$scope.usuario = {};
	// Flag para avisar se existe usuário logado
	$scope.isLogado = false;
	// flag para exibir mensagem de erro na tela
	$scope.exibirMensagemErro = false;
	
	// Função que verifica se o código da tela que foi aberta é a mesmo que foi
	// passado como argumento, nesse caso devolve a palavra 'active'
	$scope.isAtivo = function(tela) {
		return TelaHelper.tela == tela ? 'active' : '';
	};
	
	// função que realiza o login no sistema
	$scope.doLogin = function() {
		$scope.exibirMensagemErro = false;
		// primeiro criamos uma variável data que possui um atributo contexto,
		// este é um objeto que possui um atributo 'usuario' que reebe o usuario
		// que está no scope da controller.
		var data = {'contexto' : {
			'usuario' : $scope.usuario
		}};
		
		// JSON é um objeto nativo do JavaScript e serve para converter variaveis
		// de Object para uma String e o contrário também.
		var data1 = JSON.stringify(data);
		// AJAX (Asynchronous JavaScript e XML) é a forma como requisições são feitas
		// em javascript, aqui usamos o ajax do jquery (http://api.jquery.com/jquery.ajax/)
		jQuery.ajax({
			// variável urlPath definida na linha 15
		    url: urlPath + 'login.action',
		    // atributo onde passamos os parâmetros da requisição 
		    data: data1,
		    // definição do tipo dos dados
		    dataType: 'json',
		    // definição do tipo de retorno
		    contentType: 'application/json',
		    // tipo da requisição
		    type: 'POST',
		    // Define se o execução deve esperar o retorno ou não
		    async: false,
		    success: function (response) {
		    	// a partir da variável 'response' é possível acessar 
		    	var usuario = response.contexto.usuario
		    	if (usuario == null) {
	    			$scope.exibirMensagemErro = true;
	    			return;
		    	}
		    	$scope.usuario = usuario;
		    	StorageHelper.setItem(CHAVE_STORAGE, usuario);
		    	$scope.isLogado = true;
		    }
		});
	};
	
	// 
	$scope.getMensagemApresentacao = function() {
		return $sce.trustAsHtml("Olá, " + $scope.usuario.nome);
	}
	
	$scope.doLogout	 = function() {
		var data = {contexto : {
			usuario : $scope.usuario
		}};
		
		var data1 = JSON.stringify(data);
		jQuery.ajax({
		    url: urlPath + 'logout.action',
		    dataType: 'json',
		    contentType: 'application/json',
		    data: data1,
		    type: 'POST',
		    async: false,
		    success: function (response) {
				StorageHelper.removeItem(CHAVE_STORAGE);
				$scope.usuario = {};
				$scope.isLogado = false;
		    }
		});
	};
	
	$scope.isLogged = function () {
		var usuario = StorageHelper.getItem(CHAVE_STORAGE);
		if (usuario != null) {
			var agora = new Date().getTime()
			var inicioSessao = usuario.startSession;
			if (inicioSessao + 1200000 <= agora) {
				$scope.usuario = {};
				$scope.isLogado = false;
			} else {
				$scope.usuario = usuario;
				$scope.isLogado = true;
			}
		} else {
			$scope.usuario = {};
			$scope.isLogado = false;
		}
		$scope.$applyAsync();
	};
	
	setTimeout(function() {
		$scope.isLogged();
	}, 0);
	
}]);