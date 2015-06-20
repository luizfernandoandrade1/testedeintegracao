package model;

import java.util.ArrayList;
import java.util.List;

public class ClasseIntegracao {

	private String nome;
	private String id;
	private int fi;
	private List<ClasseIntegracao> lstDependencias;
	private List<ClasseIntegracao> lstDosDependentes;
	private List<Integer> fit;
	private Boolean ativo = true;
	
	public ClasseIntegracao() {
		inicializaLst();
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public ClasseIntegracao(String nome, String id) {
		this.nome = nome;
		this.id = id;
		inicializaLst();
	}

	private void inicializaLst() {
		lstDependencias = new ArrayList<ClasseIntegracao>();
		lstDosDependentes = new ArrayList<ClasseIntegracao>();
		fit = new ArrayList<Integer>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNumDependencias() {
		return fi;
	}

	public List<ClasseIntegracao> getLstDependencias() {
		return lstDependencias;
	}

	public void setLstDependencias(List<ClasseIntegracao> lstDependencias) {
		this.lstDependencias = lstDependencias;
	}

	public void setLstDependencias(ClasseIntegracao lstDependencias) {
		this.lstDependencias.add(lstDependencias);
	}

	public List<ClasseIntegracao> getLstDosDependentes() {
		return lstDosDependentes;
	}

	public void setLstDosDependentes(List<ClasseIntegracao> lstDosDependentes) {
		this.lstDosDependentes = lstDosDependentes;
	}

	public void setLstDosDependentes(ClasseIntegracao lstDosDependentes) {
		this.lstDosDependentes.add(lstDosDependentes);
	}

	public int getFi() {
		return fi;
	}

	public void setFi() {
		this.fi = lstDosDependentes.size();
	}

	public List<Integer> getFit() {
		return fit;
	}

	public Boolean removeLstDosDependentes(String classe) {
		Boolean retorno = false;
		try {
			int posicao = 0;
			int i = 0;
			while (!retorno || i < lstDosDependentes.size()) {
				if (lstDosDependentes.get(i).getNome().equals(classe)) {
					posicao = i;
					retorno = true;
				}
				i++;
			}
			lstDosDependentes.remove(posicao);
			return retorno;
		} catch (Exception e) {
			return retorno;
		}

	}

	public Boolean removeLstDependencias(String classe) {
		Boolean retorno = false;
		try {
			int posicao = 0;
			int i = 0;
			while (retorno || i < lstDependencias.size()) {
				if (lstDependencias.get(i).getNome().equals(classe)) {
					posicao = i;
					retorno = true;
				}
				i++;
			}
			lstDependencias.remove(posicao);
			return retorno;
		} catch (Exception e) {
			return retorno;
		}

	}

	public void setFit() {
		int somaFit = 0;
		if (this.ativo) {
			for (int i = 0; i < lstDependencias.size(); i++) {
				if (lstDependencias.get(i).getAtivo())
					somaFit += lstDependencias.get(i).getFi();
			}

			this.fit.add(somaFit);
		} else {
			this.fit.add(-1);
		}
	}

	public int ultimoFit() {
		int sizeLstFit = fit.size() - 1;
		return fit.get(sizeLstFit);
	}
}
