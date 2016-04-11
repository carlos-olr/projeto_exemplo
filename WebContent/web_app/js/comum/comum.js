// Um módulo angular serve para encapsular diferentes partes da aplicação, o objetivo é separar
// lógicas de negócio e códigos que possuam objetivos comuns.
// Para se definir um módulo usamos: angular.module('NOME_MODULO', ['DEPENDENCIA_1', 'DEPENDENCIA_2']);
// O módulo ui-bootstrap serve para facilitar a integração angular->bootstrap CSS
angular.module('fatec', ['ui.bootstrap']);

var StorageHelper = (function(){

	var SH = {};

	SH.setItem = function(chave, valor) {
		window.localStorage.setItem(chave, angular.toJson(valor));
	};

	SH.getItem = function(chave, valor) {
		return angular.fromJson(window.localStorage.getItem('usuario'));
	};

	SH.removeItem = function(chave) {
		window.localStorage.removeItem(chave);
	}

	return SH;

})();

var TelaHelper = (function(){
	
	var TH = {};
	
	TH.tela = '';

	return TH;

})();

jQuery(document).on('mouseup','.btn', function(){
    $(this).blur();
});