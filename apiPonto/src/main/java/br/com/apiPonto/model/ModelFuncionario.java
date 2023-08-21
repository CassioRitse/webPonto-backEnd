package br.com.apiPonto.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ModelFuncionario {
	private UUID idFuncionario;
	private List<ModelHorario> listHorariosTrabalho; // Lista dos Horarios de Trabalho
	private List<ModelHorario> listPontosMarcados; // Lista dos pontos Batidos
	private List<ModelHorario> listaHorasExtras;
	private List<ModelHorario> listaHorasAtrasada;

	public ModelFuncionario() {
		this.idFuncionario = UUID.randomUUID();
		listHorariosTrabalho = new ArrayList<>(3);
		listPontosMarcados = new ArrayList<>();
		listaHorasExtras = new ArrayList<>();
		listaHorasAtrasada = new ArrayList<>();
	}

	public UUID getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(UUID idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public List<ModelHorario> getListHorariosTrabalho() {
		return listHorariosTrabalho;
	}

	public void setListHorariosTrabalho(List<ModelHorario> listHorariosTrabalho) {
		this.listHorariosTrabalho = listHorariosTrabalho;
	}

	public List<ModelHorario> getListPontosMarcados() {
		return listPontosMarcados;
	}

	public void setListPontosMarcados(List<ModelHorario> listPontosMarcados) {
		this.listPontosMarcados = listPontosMarcados;
	}

	public List<ModelHorario> getListaHorasExtras() {
		return listaHorasExtras;
	}

	public void setListaHorasExtras(List<ModelHorario> listaHorasExtras) {
		this.listaHorasExtras = listaHorasExtras;
	}

	public List<ModelHorario> getListaHorasAtrasada() {
		return listaHorasAtrasada;
	}

	public void setListaHorasAtrasada(List<ModelHorario> listaHorasAtrasada) {
		this.listaHorasAtrasada = listaHorasAtrasada;
	}

}
