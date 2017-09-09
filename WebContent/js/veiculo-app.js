
'use strict';

var app = angular.module(
    'App',
    ['ngRoute', 'ngResource']);

app.config(function($routeProvider) {
	
	var scripts = document.getElementsByTagName("script");
    var currentScriptPath = scripts[scripts.length-1].src;
    var baseUrl = currentScriptPath.substring(0, currentScriptPath.lastIndexOf('/') + 1);
    console.log("currentScriptPath:"+currentScriptPath);
    
    var getUrl = window.location;
    baseUrl = getUrl.protocol + "//" + getUrl.host + getUrl.pathname; // "/" + getUrl.pathname.split('/')[1];
    console.log("baseUrl:"+baseUrl);

    $routeProvider.when('/', {
        templateUrl : baseUrl + 'partials/veiculos.html',
        controller: 'AppCtrl'
    }).when('/usuarios', {
        templateUrl : baseUrl + 'partials/usuarios.html',
        controller: 'AppCtrl'
    }).when('/usuarios-editar', {
        templateUrl : baseUrl + 'partials/usuarios-editar.html',
        controller: 'AppCtrl'
    }).otherwise({
        redirectTo : baseUrl + 'partials/teste.htm',
        controller: 'AppCtrl'
    });

});

app.controller(
    'AppCtrl',
    function($scope, $http, $rootScope, $timeout,
                 $location, $filter) {

    
    	//$scope.entries = [];
    	var getUrl = window.location;
    	$scope.baseUrl = getUrl.protocol + "//" + getUrl.host + getUrl.pathname;
    	
    	$scope.irParaUsuario = function() {
			$location.path('/usuarios');
		}
    	$scope.irParaEdicaoUsuario = function() {
			$location.path('/usuarios-editar');
		}
    	
    	$scope.onItemClicked = function(item) {
			$scope.selectedItem = item;
			$scope.entry = null;
		};

        $scope.buscar = function() {

        	$scope.selectedItem = null;
        	$scope.entry = null;

            $http({method: 'GET', url: $scope.baseUrl +'api/veiculos'})
                .then(function successCallback(response) {
                    $scope.errauthc = false;
                    $scope.entries = response.data;
                    //$location.path('/app');
                }, function errorCallback(response) {
                    $scope.errauthc = true;
                });


        }
        
        $scope.editar = function() {
        	
        	$scope.entry = angular.copy($scope.selectedItem);
        }
        
        $scope.criar = function() {
			$scope.selectedItem = null;
			$scope.entry = {};
        }
        
        $scope.salvarOuAtualizar = function() {
        	if($scope.entry.id != null){
        		 $http({method: 'PUT',
        			 	url: $scope.baseUrl + 'api/veiculos/' + $scope.entry.id,
        			 	data: $scope.entry })
                 .then(function successCallback(response) {
                     $scope.errauthc = false;
                     $scope.entry.id = response.data;
                     
                     
                     for ( var i = 0, len = $scope.entries.length; i < len; i++) {
     					if ($scope.entries[i].id == $scope.entry.id) {
     						$scope.entries[i] = $scope.selectedItem = $scope.entry;
     					}
     				}
                     $scope.entry = null;
                    //$location.path('/app');
                 }, function errorCallback(response) {
                     $scope.errauthc = true;
                 });

        	} else {
	        	$http({method: 'POST', 
	        			url: $scope.baseUrl + 'api/veiculos/',
	        			data:  $scope.entry })
	            .then(function successCallback(response) {
	                $scope.errauthc = false;
	                $scope.entry.id = response.data;
	                $scope.entries.push($scope.entry);
	                $scope.entry = null;
	                //$location.path('/app');
	            }, function errorCallback(response) {
	                $scope.errauthc = true;
	            });
        	}
        	
			
        }
        
        $scope.remover = function() {
        	$http({method: 'DELETE', 
	    			url: $scope.baseUrl + 'api/veiculos/'+ $scope.selectedItem.id})
	        .then(function successCallback(response) {
	            $scope.errauthc = false;
	            $scope.entries = $filter('filter')($scope.entries, function(item){
										return item != $scope.selectedItem;
							      });
	            
	            $scope.selectedItem = null;
	        }, function errorCallback(response) {
	            $scope.errauthc = true;
	        });
        }
        
        $scope.$seEstaAtivo = function(item) {
		    var active = false;
		    if (item && $scope.selectedItem) {
		    	active = $scope.selectedItem.id == item.id; 
		    }
		    return active;
		}
       
//        $scope.atualizarOk = function(data, textStatus, jqXHR) {
//        	$("#global-message").addClass("alert-success").text("Usuário atualizado com sucesso.").show();
//        }
        
//        $scope.tratarErro = function tratarErro(request) {
//        	switch (request.status) {
//        	case 404:
//        		$("#global-message").addClass("alert-danger").text("O registro solicitado não foi encontrado!").show();
//        	break;
//        	}
//        }

    });