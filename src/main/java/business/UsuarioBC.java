package business;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import dao.Repositorio;
import exception.UsuarioNaoEncontradoException;
import exception.ValidacaoException;
import model.Cep;
import model.Usuario;

@ApplicationScoped
public class UsuarioBC {
	
	@Inject 
	private Repositorio repositorio;
	
	@PostConstruct
	public void inicializar() {
		Calendar dataNascimento = Calendar.getInstance();
		Usuario usuario = new Usuario();
		usuario.setNome("José Rexona");
		usuario.setCpf("12334567890");

		usuario.setEmail("jose.rexona@gmail.com");
		usuario.setSenha("12345");
		dataNascimento.set(1950, 10, 11);
		usuario.setData(dataNascimento.getTime());
		Cep cepJose = new Cep();
		cepJose.setRegiao("60000");
		cepJose.setSufixo("100");
		usuario.setCep(cepJose);
		repositorio.inserir(usuario);
		
		usuario = new Usuario();
		usuario.setNome("Maria Antonieta");
		usuario.setCpf("00030060090");

		usuario.setEmail("maria.antonieta@gmail.com");
		usuario.setSenha("123456");
		dataNascimento.set(1950, 12, 25);
		usuario.setData(dataNascimento.getTime());
		Cep cepMaria = new Cep();
		cepMaria.setRegiao("76856");
		cepMaria.setSufixo("300");
		usuario.setCep(cepMaria);
		repositorio.inserir(usuario);
	}

	public List<Usuario> selecionar() {
		return repositorio.selecionar(Usuario.class);
	}
	
	public Usuario selecionar(Long id) throws UsuarioNaoEncontradoException {
		Usuario usuario = repositorio.selecionar(Usuario.class, id);
		if (usuario == null) {
			throw new UsuarioNaoEncontradoException();
		}
		return usuario;
	}
	
	public Long inserir(Usuario usuario) throws ValidacaoException {
		validar(usuario);
		return repositorio.inserir(usuario);
	}
	
	public void atualizar(Usuario usuario) throws UsuarioNaoEncontradoException, ValidacaoException {
		validar(usuario);
		if (!repositorio.atualizar(usuario)) {
			throw new UsuarioNaoEncontradoException();
		}
	}
	
	public Usuario excluir(Long id) throws UsuarioNaoEncontradoException {
		
		Usuario usuario = repositorio.excluir(Usuario.class, id);
		if (usuario == null) {
			throw new UsuarioNaoEncontradoException();
		}
		return usuario;
	}

	
	private void validar(Usuario usuario) throws ValidacaoException {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
		if (!violations.isEmpty()) {
			ValidacaoException validacaoException = new ValidacaoException();
			
			for (ConstraintViolation<Usuario> violation : violations) {
				String entidade = violation.getRootBeanClass().getSimpleName();
				String propriedade = violation.getPropertyPath().toString();
				String mensagem = violation.getMessage();
				validacaoException.adicionar(entidade, propriedade, mensagem);
			}
			throw validacaoException;
		}
	}
}
	
