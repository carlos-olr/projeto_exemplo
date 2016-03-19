var app = angular.module('fatec', []);
var urlPath = "http://localhost:8080/projeto_exemplo/Usuario!";

app.controller('usuarioCtrl', function ($scope, $http, $timeout) {
	
	$scope.usuario = {nome:'Carlos'};
	
});