package br.com.apiPonto.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.apiPonto.model.ModelHorario;
import br.com.apiPonto.repository.Banco_Dados;

public class ServiceMarcarHoraio {
	private Banco_Dados bd = Banco_Dados.getInstancia();

	public boolean addHorarioDeTrabalho(LocalTime entrada, LocalTime saida) {
		ModelHorario novoHorario = new ModelHorario(entrada, saida);
		return bd.addHorarioDeTrabalho(novoHorario) ? true : false;
	}

	public Optional<Boolean> removeHorarioDeTrabalho(UUID idHorario) {
		for (ModelHorario horario : bd.getAllHorariosTrabalho()) {
			if (horario.getIdhorario().equals(idHorario)) {
				return Optional.of(bd.removeHorarioDeTrabalho(horario));
			}
		}
		return Optional.empty();
	}
	
	public void removeAllHorariosDeTrabalho() {
		bd.getAllHorariosTrabalho().clear();
		bd.getAllHorasAtrasadas().clear();
		bd.getAllHorasExtras().clear();
		bd.getAllPontos().clear();
	}

	public Boolean isCheioHorario() {
		return getAllHorariosTrabalho().size() == 3 ? true : false;
	}

	public List<ModelHorario> getAllHorariosTrabalho() {
		return bd.getAllHorariosTrabalho();
	}
}
