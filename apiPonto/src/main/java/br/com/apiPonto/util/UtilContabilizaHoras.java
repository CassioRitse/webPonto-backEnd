package br.com.apiPonto.util;

import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.apiPonto.model.ModelHorario;

public class UtilContabilizaHoras {

	public static Optional<ModelHorario> getExtraDepoisDoHorario(ModelHorario horario, ModelHorario ponto) {
		if (isHorarioPM(horario.getSaida()) && !isHorarioPM(ponto.getSaida())) {
			if(ponto.getSaida().isBefore(horario.getSaida())){
				return Optional.empty();
			}
		}

		if (ponto.getSaida().isAfter(horario.getSaida())) {
			return Optional.of(new ModelHorario(horario.getSaida(), ponto.getSaida()));
		}

		return Optional.empty();
	}

	public static Optional<ModelHorario> getExtraAntesDoHorario(ModelHorario horario, ModelHorario ponto) {
		if (getChegouAtrasado(horario, ponto).isPresent()) {
			return Optional.empty();
		}

		if (ponto.getEntrada().isAfter(horario.getSaida()) && !isHorarioPlantao(horario)) {
			return Optional.empty();
		}
		
		if (ponto.getEntrada().isAfter(horario.getSaida())){
			return Optional.of(new ModelHorario(ponto.getEntrada(), horario.getEntrada()));
		}

		return Optional.of(new ModelHorario(ponto.getEntrada(), horario.getEntrada()));
	}

	public static Optional<ModelHorario> getChegouAtrasado(ModelHorario horario, ModelHorario ponto) {
		if (isHorarioPlantao(horario)) {
			if (ponto.getEntrada().isAfter(horario.getEntrada()) && ponto.getSaida().isBefore(horario.getSaida())) {
				return Optional.of(new ModelHorario(horario.getEntrada(), ponto.getEntrada()));
			}
		}

		if (ponto.getEntrada().isAfter(horario.getEntrada()) && ponto.getEntrada().isBefore(horario.getSaida())) {
			return Optional.of(new ModelHorario(horario.getEntrada(), ponto.getEntrada()));
		}
		
		return Optional.empty();
	}
	
	
	public static Optional<ModelHorario> getSaiuAdiantado(ModelHorario horario, ModelHorario ponto){
		if (ponto.getSaida().isBefore(horario.getSaida())) {
			return Optional.of(new ModelHorario(ponto.getSaida(), horario.getSaida()));
		}
		
		return Optional.empty();
	}

	public static Optional<ModelHorario> getTrabalhouIntervalo(ModelHorario horario, ModelHorario ponto) {
		if (ponto.getEntrada().isBefore(horario.getEntrada()) && ponto.getSaida().isAfter(horario.getSaida())) {
			return Optional.of(new ModelHorario(horario.getEntrada(), horario.getSaida()));
		}
		return Optional.empty();
	}

	public static boolean isHorarioPlantao(ModelHorario horario) {
		if (isHorarioPM(horario.getEntrada()) && !isHorarioPM(horario.getSaida())) {
			return true;
		} else {
			return false;
		}
	}

	public static List<ModelHorario> getIntervaloHorario(List<ModelHorario> listHorarios) {
		List<ModelHorario> listaIntevalos = new ArrayList<>();

		for (int i = 0; i < listHorarios.size() - 1; i++) {
			LocalTime intervaloEntrada = listHorarios.get(i).getSaida();
			LocalTime intervaloSaida = listHorarios.get(i + 1).getEntrada();
			listaIntevalos.add(new ModelHorario(intervaloEntrada, intervaloSaida));
		}

		return listaIntevalos;

	}

	public static boolean isHorarioPM(LocalTime horario) {
		// verifica se o horario é a tarde ou a noite: 0 para (AM) - manhã e 1 para (PM)
		// - tarde.
		return horario.get(ChronoField.AMPM_OF_DAY) == 1 ? true : false;
	}
}
