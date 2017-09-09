package rest;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import model.Cep;

public class CepAdapter extends XmlAdapter<String, Cep> {
	
	
	@Override 
	public Cep unmarshal(String value) throws Exception {
		if (value == null || value.trim().equals("")) {
			return null;
		}
		String[] valoresCep = value.split("-");
		Cep cep = new Cep();
		cep.setRegiao(valoresCep[0]);
		cep.setSufixo(valoresCep.length < 2 ? "" : valoresCep[1]);
		return cep; 
	}
	
	@Override 
	public String marshal(Cep value) throws Exception {
		if (value == null) {
			return"";
		}
		return value.getRegiao() + "-" + value.getSufixo();
	}
}

