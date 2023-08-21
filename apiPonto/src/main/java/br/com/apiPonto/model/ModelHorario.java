package br.com.apiPonto.model;

import java.time.LocalTime;
import java.util.UUID;

public class ModelHorario {
	private UUID idhorario;
	private LocalTime entrada;
	private LocalTime saida;
	private static final LocalTime AUSENTE = LocalTime.of(0, 0);

	public ModelHorario(LocalTime entrada) {
		this.idhorario = UUID.randomUUID();
		this.entrada = entrada;
		this.saida = AUSENTE;
	}
	
	public ModelHorario(LocalTime entrada, LocalTime saida) {
		this.idhorario = UUID.randomUUID();
		this.entrada = entrada;
		this.saida = saida;
	}

	public UUID getIdhorario() {
		return idhorario;
	}

	public void setIdhorario(UUID idhorario) {
		this.idhorario = idhorario;
	}

	public LocalTime getEntrada() {
		return entrada;
	}

	public void setEntrada(LocalTime entrada) {
		this.entrada = entrada;
	}

	public LocalTime getSaida() {
		return saida;
	}

	public void setSaida(LocalTime saida) {
		this.saida = saida;
	}
	
	@Override
	public String toString() {
		return this.entrada + " - " + this.saida;
	}

}
