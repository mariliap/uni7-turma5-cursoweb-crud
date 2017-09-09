package business;

import java.math.BigDecimal;
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
import exception.ValidacaoException;
import exception.VeiculoNaoEncontradoException;
import model.Veiculo;

@ApplicationScoped
public class VeiculoBC {
	
	@Inject 
	private Repositorio repositorio;
	
	@PostConstruct
	public void inicializar() {
		Calendar dataEmplacamento = Calendar.getInstance();
		Veiculo veiculo = new Veiculo();
		veiculo.setNomeProprietario("José Rexona");
		veiculo.setValorIPVA(new BigDecimal("1500"));
		veiculo.setPlaca("AAA-9999");
		dataEmplacamento.set(2015, 10, 11);
		veiculo.setDataEmplacamento(dataEmplacamento.getTime());
		repositorio.inserir(veiculo);
	}

	public List<Veiculo> buscar() {
		return repositorio.selecionar(Veiculo.class);
	}
	
	public Veiculo buscar(Long id) throws VeiculoNaoEncontradoException {
		Veiculo veiculo = repositorio.selecionar(Veiculo.class, id);
		if (veiculo == null) {
			throw new VeiculoNaoEncontradoException();
		}
		return veiculo;
	}
	
	public Long inserir(Veiculo veiculo) throws ValidacaoException {
		validar(veiculo);
		return repositorio.inserir(veiculo);
	}
	
	public void atualizar(Veiculo veiculo) throws VeiculoNaoEncontradoException, ValidacaoException {
		validar(veiculo);
		if (!repositorio.atualizar(veiculo)) {
			throw new VeiculoNaoEncontradoException();
		}
	}
	
	public Veiculo excluir(Long id) throws VeiculoNaoEncontradoException {
		
		Veiculo veiculo = repositorio.excluir(Veiculo.class, id);
		if (veiculo == null) {
			throw new VeiculoNaoEncontradoException();
		}
		return veiculo;
	}

	
	private void validar(Veiculo veiculo) throws ValidacaoException {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Veiculo>> violations = validator.validate(veiculo);
		if (!violations.isEmpty()) {
			ValidacaoException validacaoException = new ValidacaoException();
			
			for (ConstraintViolation<Veiculo> violation : violations) {
				String entidade = violation.getRootBeanClass().getSimpleName();
				String propriedade = violation.getPropertyPath().toString();
				String mensagem = violation.getMessage();
				validacaoException.adicionar(entidade, propriedade, mensagem);
			}
			throw validacaoException;
		}
	}
}
	
