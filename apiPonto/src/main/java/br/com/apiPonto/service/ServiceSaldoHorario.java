package br.com.apiPonto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.apiPonto.model.ModelHorario;
import br.com.apiPonto.repository.Banco_Dados;
import br.com.apiPonto.util.UtilContabilizaHoras;

public class ServiceSaldoHorario {
	private Banco_Dados bd = Banco_Dados.getInstancia();
	
	//estive em primeiro momento vizualizando uma ideia
	//na qual traria maior robustes ao codigo, no entanto acabei me confundido nos requisitos
	//gostaria de ter tido tempo para refazer a ideia
	//a principio eu seria facil apenas colocar duas verificação
	//se o funcionario já bateu o ponto antes ou não
	//mas ficaria insustentavel a escabilidade

	public List<ModelHorario> calcularAtraso(ModelHorario ponto) {
		List<ModelHorario> listAtrasos = new ArrayList<>();
		List<ModelHorario> listHorario = bd.getAllHorariosTrabalho();

		for (ModelHorario horario : listHorario) {
			UtilContabilizaHoras.getSaiuAdiantado(horario, ponto).ifPresent(atraso -> {
				listAtrasos.add(atraso);
				salvarAtraso(atraso);
			});
		}

		return listAtrasos;
	}

	public List<ModelHorario> calcularExtra(ModelHorario ponto) {
		List<ModelHorario> listExtras = new ArrayList<>();
		List<ModelHorario> listHorario = bd.getAllHorariosTrabalho();
		List<ModelHorario> intervalos = UtilContabilizaHoras.getIntervaloHorario(listHorario);

		UtilContabilizaHoras.getExtraAntesDoHorario(listHorario.get(0), ponto)
				.ifPresent(horarioExtra -> listExtras.add(horarioExtra));
		
		if (intervalos.size() == 0) {
			// tem não possui intevalo
			UtilContabilizaHoras.getExtraDepoisDoHorario(listHorario.get(0), ponto)
					.ifPresent(horarioExtra -> listExtras.add(horarioExtra));
			
		}else if (intervalos.size() == 1) {
			// tem apenas 1 intevalo
			if (UtilContabilizaHoras.getTrabalhouIntervalo(intervalos.get(0), ponto).isPresent()) {
				listExtras.add(intervalos.get(0));
				Optional<ModelHorario> horarioApos = UtilContabilizaHoras.getExtraDepoisDoHorario(listHorario.get(1),
						ponto);
				if (horarioApos.isPresent()) {
					listExtras.add(horarioApos.get());
				}
			} else {
					UtilContabilizaHoras.getExtraDepoisDoHorario(listHorario.get(0), ponto)
					.ifPresent(horarioExtra -> listExtras.add(horarioExtra));
			}
			
		}else if (intervalos.size() == 2) {
			// tem apenas 2 intevalo
			if (UtilContabilizaHoras.getTrabalhouIntervalo(intervalos.get(1), ponto).isPresent()) {
				listExtras.add(intervalos.get(1));
				Optional<ModelHorario> horarioApos = UtilContabilizaHoras.getExtraDepoisDoHorario(listHorario.get(2),
						ponto);
				if (horarioApos.isPresent()) {
					listExtras.add(horarioApos.get());
				}
			} else {
				UtilContabilizaHoras.getExtraDepoisDoHorario(listHorario.get(1), ponto)
						.ifPresent(horarioExtra -> listExtras.add(horarioExtra));
			}
		}

		for (ModelHorario extras : listExtras) {
			salvarExtra(extras);
		}

		return listExtras;
	}

	public List<ModelHorario> getHorasExtras() {
		return bd.getAllHorasExtras();
	}

	public List<ModelHorario> getHorasAtrasadas() {
		return bd.getAllHorasAtrasadas();
	}

	private void salvarExtra(ModelHorario novaExtra) {
		bd.addNovaHoraExtra(novaExtra);
	}

	private void salvarAtraso(ModelHorario novoAtraso) {
		bd.addNovaHoraAtrasada(novoAtraso);
	}

}
