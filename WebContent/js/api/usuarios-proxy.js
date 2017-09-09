var UsuariosProxy = {
		url : window.location.protocol + "//" + window.location.host + "/"+ window.location.pathname.substring(1, window.location.pathname.length).substring(0, window.location.pathname.substring(1, window.location.pathname.length).indexOf('/')) + "/api/usuarios",
		selecionar : function(id) {
			return $.ajax(
					{type : "GET",
					 url : this.url + "/" + id});
		},
		selecionarTodos : function() {
			return $.ajax({type : "GET",
							url : this.url});
		},
		inserir : function(usuario) {
			return $.ajax({type : "POST",
							url : this.url,
							data : JSON.stringify(usuario),
							contentType : "application/json"});
		},
		atualizar : function(id, usuario) {
			return $.ajax({type : "PUT",
							url : this.url + "/" + id,
							data : JSON.stringify(usuario),
							contentType : "application/json"});
		},
		excluir : function(id) {
			return $.ajax({type : "DELETE",url : this.url + "/" + id});
		},

};

