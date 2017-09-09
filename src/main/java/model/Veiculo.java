package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import rest.DateAdapter;

public class Veiculo extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = 3491501750697564969L;
	
	@NotNull
	private String placa;
	
	@NotNull
	private String nomeProprietario; 
	
	@NotNull
	@Past
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date dataEmplacamento;
	
	private BigDecimal valorIPVA;
	
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getNomeProprietario() {
		return nomeProprietario;
	}
	public void setNomeProprietario(String nomeProprietario) {
		this.nomeProprietario = nomeProprietario;
	}
	public Date getDataEmplacamento() {
		return dataEmplacamento;
	}
	public void setDataEmplacamento(Date dataEmplacamento) {
		this.dataEmplacamento = dataEmplacamento;
	}
	public BigDecimal getValorIPVA() {
		return valorIPVA;
	}
	public void setValorIPVA(BigDecimal valorIPVA) {
		this.valorIPVA = valorIPVA;
	}

	
}




