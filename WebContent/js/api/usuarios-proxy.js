var UsuariosProxy = {url : "api/usuarios",selecionar : function(id) {return $.ajax({type : "GET",url : this.url + "/" + id});},selecionarTodos : function() {return $.ajax({type : "GET",url : this.url});}};

$(function() {UsuariosProxy.selecionarTodos().done(buscarOk);});function buscarOk(usuarios) {var corpo = $('#usuarios tbody');corpo.empty();if (usuarios.length) {$.each(usuarios, function(i, usuario) {corpo.append($('<tr>').append($('<td>').append($('<a>').attr('href','usuarios-editar.html?id=' + usuario.id).text(usuario.cpf)),$('<td>').text(usuario.nome),$('<td>').text(usuario.email),$('<td>').text(usuario.data)));});




} else {corpo.append($('<tr>').append($('<td>').attr('colspan', 4).text('Nenhum registro encontrado!')));}}
