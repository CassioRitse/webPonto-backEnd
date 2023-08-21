package br.com.apiPonto.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.apiPonto.model.ModelFuncionario;
import br.com.apiPonto.model.ModelHorario;

public class Banco_Dados implements BDInterface {
	private static Banco_Dados instancia;
	private ModelFuncionario funcionario;

	private Banco_Dados() {
		funcionario = new ModelFuncionario();
	}

	// Padrão de Projeto Singleton
	public static Banco_Dados getInstancia() {
		if (instancia == null) {
			instancia = new Banco_Dados();
		}

		return instancia;
	}

	@Override
	public ModelHorario abrirNovoPonto(ModelHorario novoHorario) {
		funcionario.getListPontosMarcados().add(novoHorario);
		return novoHorario;
	}

	@Override
	public boolean addHorarioDeTrabalho(ModelHorario novoHorario) {
		return funcionario.getListHorariosTrabalho().add(novoHorario) ? true : false;
	}

	@Override
	public boolean removeHorarioDeTrabalho(ModelHorario horario) {
		return funcionario.getListHorariosTrabalho().remove(horario);
	}

	@Override
	public List<ModelHorario> getAllHorariosTrabalho() {
		return funcionario.getListHorariosTrabalho();
	}

	@Override
	public List<ModelHorario> getAllPontos() {
		return funcionario.getListPontosMarcados();
	}

	@Override
	public Optional<ModelHorario> findPontoEmAberto(UUID idHorario) {
		Optional<ModelHorario> pontoEmAberto = funcionario.getListPontosMarcados().stream()
				.filter((horario) -> horario.getIdhorario().equals(idHorario))
				.findFirst();

		return pontoEmAberto;
	}

	@Override
	public boolean addNovaHoraExtra(ModelHorario novoHorario) {
		return funcionario.getListaHorasExtras().add(novoHorario);
	}

	@Override
	public boolean addNovaHoraAtrasada(ModelHorario novoHorario) {
		return funcionario.getListaHorasAtrasada().add(novoHorario);
	}

	@Override
	public List<ModelHorario> getAllHorasExtras() {
		return funcionario.getListaHorasExtras();
	}

	@Override
	public List<ModelHorario> getAllHorasAtrasadas() {
		return funcionario.getListaHorasAtrasada();
	}

}
