package model;

import javax.validation.constraints.Size;

public class Cep {
	
	@Size(min = 5, max = 5)
	private String regiao;
	
	@Size(min = 3, max = 3)
	private String sufixo;

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public String getSufixo() {
		return sufixo;
	}

	public void setSufixo(String sufixo) {
		this.sufixo = sufixo;
	}
	
	
}
