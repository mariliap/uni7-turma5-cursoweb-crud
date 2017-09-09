package exception;

public class VeiculoNaoEncontradoException extends Exception {

	private static final long serialVersionUID = -1981138569701742549L;

	public VeiculoNaoEncontradoException() {
		super("Veículo não está cadastrado!");
	}
}
