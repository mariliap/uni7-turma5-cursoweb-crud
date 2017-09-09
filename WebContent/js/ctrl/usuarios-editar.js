$(function() {
	var id = obterParametroDaUrlPorNome('id');
	if (id) {
		UsuariosProxy.selecionar(id).done(obterOk).fail(tratarErro);
	}
	$("#cep").mask("?99999-999");
	
	$("#salvar").click(function(event) {
		limparMensagensErro();
		
		var usuario = {id : $("#id").val(),
						cpf : $("#cpf").val(),
						nome : $("#nome").val(),
						data : $("#data").val(),
						email : $("#email").val(),
						senha : $("#senha").val(),
						cep : $("#cep").val()};
		
		if (usuario.id) {
			UsuariosProxy.atualizar(usuario.id, usuario).done(atualizarOk).fail(tratarErro);
		} else {
			UsuariosProxy.inserir(usuario).done(inserirOk).fail(tratarErro);
		}
	});
	
	$("#excluir").click(function(event) {
		var id = $("#id").val();
		UsuariosProxy.excluir(id).done(excluirOk).fail(tratarErro);
	});
	
	function inserirOk(data, textStatus, jqXHR) {
		$("#id").val(data);
		$("#global-message").addClass("alert-success").text("Usuário com id = " + data + " criado com sucesso.").show();
	}
	
	function atualizarOk(data, textStatus, jqXHR) {
		$("#global-message").addClass("alert-success").text("Usuário atualizado com sucesso.").show();
	}
	
	function obterOk(data) {
		$("#id").val(data.id);
		$("#cpf").val(data.cpf);
		$("#nome").val(data.nome);
		$("#data").val(data.data);
		$("#email").val(data.email);
		$("#senha").val(data.senha);
		$("#cep").val(data.cep);
	}
	
	function excluirOk(data, textStatus, jqXHR) {
		$("#id").val(null);$("#cpf").val(null);
		$("#nome").val(null);$("#data").val(null);
		$("#email").val(null);$("#senha").val(null);
		$("#global-message").addClass("alert-success").text("Usuário excluído com sucesso.").show();
	}

	function limparMensagensErro() {
		$("#global-message").removeClass("alert-danger alert-success").empty().hide();
		$(".control-label").parent().removeClass("has-success");
		$(".text-danger").parent().removeClass("has-error");
		$(".text-danger").hide();
	}

	
	function tratarErro(request) {
		switch (request.status) {
			case 406:
				$("form input").each(function() {
					var id = $(this).attr("id"); 
					var message = null;
					$.each(request.responseJSON, function(index, value) {
						var propriedade = value.propriedade.split('.');
						var campo = propriedade[0];
						var atributo = propriedade[1]==null? "" : propriedade[1]+": ";
						if (id == campo) { 
							if(message!=null){
								message += ", ";
							} else {
								message = "";
							}
							message += atributo + " " + value.mensagem; 
						}
					});
					
					if (message) {
						$("#" + id).parent().addClass("has-error");
						$("#" + id + "-message").html(message).show();
						$(this).focus();
					} else {
						$("#" + id).parent().removeClass("has-error");
						$("#" + id + "-message").hide();
					}
				});
				
				$("#global-message").addClass("alert-danger").text("Verifique erros no formulário!").show();
				break;
			case 404:
				$("#global-message").addClass("alert-danger").text("O registro solicitado não foi encontrado!").show();
				break;
			default:
				$("#global-message").addClass("alert-danger").text("Erro inesperado.").show();
				break;
		}
	}
	
	function obterParametroDaUrlPorNome(name){
		name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
		var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = regex.exec(location.search);
		return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
	}
	
	
});
