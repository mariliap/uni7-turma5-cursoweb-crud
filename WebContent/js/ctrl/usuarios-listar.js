$(function() {
	UsuariosProxy.selecionarTodos().done(buscarOk);
});

function buscarOk(usuarios) {
	var corpo = $('#usuarios tbody');
	corpo.empty();
	if (usuarios.length) {
		$.each(usuarios, function(i, usuario) {
			
			var cellCpf = document.createElement('td');
			var textCpf = document.createTextNode(usuario.cpf);
			var linkCpf = document.createElement('a');
			linkCpf.setAttribute("href", "usuarios-editar.html?id=" + usuario.id);
			linkCpf.appendChild(textCpf);
			cellCpf.appendChild(linkCpf);
			
			corpo.append($('<tr>')
					.append($('<td>').append($('<a>').attr('href','usuarios-editar.html?id=' + usuario.id).text(usuario.cpf)),
							$('<td>').text(usuario.nome),
							$('<td>').text(usuario.email),
							$('<td>').text(usuario.data),
							$('<td>').text(usuario.cep)));
			}
		);
	} else {
			corpo.append($('<tr>')
					.append($('<td>').attr('colspan', 4).text('Nenhum registro encontrado!')));
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
