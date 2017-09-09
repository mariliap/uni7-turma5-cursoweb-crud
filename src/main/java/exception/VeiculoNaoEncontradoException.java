package exception;

/**
 * Classe de excecao disparada pela camada de negocio.
 * @author Fabio Barros
 */
public class VeiculoNaoEncontradoException extends Exception {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -1071896537277884578L;

	public VeiculoNaoEncontradoException() {
		super("Login ou senha invalidos!");
	}
}
