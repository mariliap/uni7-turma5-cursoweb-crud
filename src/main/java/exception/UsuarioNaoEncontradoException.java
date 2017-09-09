package exception;

/**
 * Classe de excecao disparada pela camada de negocio.
 * @author Fabio Barros
 */
public class UsuarioNaoEncontradoException extends Exception {

	private static final long serialVersionUID = 4847272129920780248L;

	public UsuarioNaoEncontradoException() {
		super("Login ou senha invalidos!");
	}
}
