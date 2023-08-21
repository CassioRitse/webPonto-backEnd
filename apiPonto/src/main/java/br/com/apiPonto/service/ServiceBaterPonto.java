package br.com.apiPonto.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import br.com.apiPonto.model.ModelHorario;
import br.com.apiPonto.repository.Banco_Dados;

public class ServiceBaterPonto {
	private Banco_Dados bd = Banco_Dados.getInstancia();

	public ModelHorario baterPonto(LocalTime entrada, LocalTime saida) {
		ModelHorario novoPonto = new ModelHorario(entrada, saida);
		return bd.abrirNovoPonto(novoPonto);
	}

	public void removeAllPontos() {
		bd.getAllPontos().clear();
	}

	// A variavel Tipo representa ENTRADA == 0 ou Saida == 1
	public ModelHorario abrirPonto(LocalTime entrada) {
		ModelHorario novoHorario = new ModelHorario(entrada);
		return bd.abrirNovoPonto(novoHorario);
	}

	public ModelHorario fecharPonto(ModelHorario pontoEmAberto, LocalTime saida) {
		pontoEmAberto.setSaida(saida);
		return pontoEmAberto;
	}

	public List<ModelHorario> getAllPontos() {
		return bd.getAllPontos();
	}

	public Optional<ModelHorario> findPontoAberto(UUID pontoEmAberto) {
		Optional<ModelHorario> opHorario = bd.findPontoEmAberto(pontoEmAberto);
		if (opHorario.isPresent()) {
			return opHorario;
		} else {
			return Optional.empty();
		}
	}

}
