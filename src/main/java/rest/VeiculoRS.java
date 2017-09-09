package rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import business.VeiculoBC;
import exception.ValidacaoException;
import exception.VeiculoNaoEncontradoException;
import model.Veiculo;

@Path("veiculos")
public class VeiculoRS {
	
	@Inject
	private VeiculoBC veiculoBC;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Veiculo> buscar() {
		return veiculoBC.buscar();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Veiculo buscar(@PathParam("id") Long id) {
		try {
			return veiculoBC.buscar(id);
		} catch (VeiculoNaoEncontradoException e) {
			throw new NotFoundException();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response inserir(Veiculo body) {
		
		try {
			Long id = veiculoBC.inserir(body);
		
			//String url = "/api/veiculo/" + id;
			return Response
					.status(Status.CREATED)//.header("Location", url)
					.entity(id)
					.build();
		} catch (ValidacaoException e) {
			return tratarValidacaoException(e);
		}
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizar(@PathParam("id") Long id, Veiculo veiculo) {
		   
		try {
			veiculo.setId(id);
			veiculoBC.atualizar(veiculo);
			return Response
					.status(Status.OK)
					.entity(id)
					.build();
		} catch (VeiculoNaoEncontradoException e) {
			throw new NotFoundException();
		} catch (ValidacaoException e) {
			return tratarValidacaoException(e);
		}
	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response excluir(@PathParam("id") Long id) {
		
		try {
			Veiculo veiculoExcluido =  veiculoBC.excluir(id);
			return Response
					.status(Status.OK)
					.entity(veiculoExcluido)
					.build();
		} catch (VeiculoNaoEncontradoException e) {
			throw new NotFoundException();
		}
	}
	
	private Response tratarValidacaoException(ValidacaoException e) {
		return Response
				.status(Status.NOT_ACCEPTABLE)
				.entity(e.getErros())
				.build();
	}

}
