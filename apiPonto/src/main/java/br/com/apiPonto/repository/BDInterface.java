package br.com.apiPonto.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.apiPonto.model.ModelHorario;

public interface BDInterface {
	public ModelHorario abrirNovoPonto(ModelHorario novoHorario);
	public boolean removeHorarioDeTrabalho(ModelHorario idHorario);
	public boolean addHorarioDeTrabalho(ModelHorario novoHorario);
	public List<ModelHorario> getAllHorariosTrabalho();
	public List<ModelHorario> getAllPontos();
	public List<ModelHorario> getAllHorasExtras();
	public List<ModelHorario> getAllHorasAtrasadas();
	public Optional<ModelHorario> findPontoEmAberto(UUID idPonto);
	public boolean addNovaHoraExtra(ModelHorario novoHorario);
	public boolean addNovaHoraAtrasada(ModelHorario novoHorario);
}
