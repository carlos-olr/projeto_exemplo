jQuery(document).on('mouseup','.btn', function(){
    $(this).blur();
});

angular.module('fatec', ['ui.bootstrap', 'ngCookies']);

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