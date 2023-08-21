package br.com.apiPonto.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalTime;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.apiPonto.model.ModelHorario;
import br.com.apiPonto.service.ServiceBaterPonto;
import br.com.apiPonto.service.ServiceMarcarHoraio;
import br.com.apiPonto.service.ServiceSaldoHorario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/baterPonto")
public class ControllerBaterPonto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServiceBaterPonto serviceBaterPonto = new ServiceBaterPonto();
	private ServiceMarcarHoraio serviceMarcarHoraio = new ServiceMarcarHoraio();
	private ServiceSaldoHorario servicoHoras = new ServiceSaldoHorario();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		JsonArray jsonArray = new JsonArray();
		for (ModelHorario horario : serviceBaterPonto.getAllPontos()) {
			JsonObject jsonHorario = new JsonObject();
			jsonHorario.addProperty("idhorario", horario.getIdhorario().toString());
			jsonHorario.addProperty("entrada", horario.getEntrada().toString());
			jsonHorario.addProperty("saida", horario.getSaida().toString());
			jsonArray.add(jsonHorario);
		}

		response.setContentType("application/json");
		response.setStatus(200);
		response.getWriter().append(jsonArray.toString());
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		serviceBaterPonto.removeAllPontos();

		response.setContentType("application/json");
		response.setStatus(200);
		response.getWriter().append("Apagou Com Sucesso!");
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		StringBuilder jsonBuilder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			jsonBuilder.append(line);
		}

		JsonObject json = JsonParser.parseString(jsonBuilder.toString()).getAsJsonObject();
		// UUID idPonto = UUID.fromString(json.get("idPonto").getAsString());
		// Boolean isSaida = json.get("tipo").getAsBoolean(); // 0 == Entrada, 1 == Saida

		LocalTime entrada = LocalTime.parse(json.get("entrada").getAsString());
		LocalTime saida = LocalTime.parse(json.get("saida").getAsString());
		
		if(serviceMarcarHoraio.getAllHorariosTrabalho().size() == 0) {
			response.setStatus(409);
			response.getWriter().append("Não existem horarios de trabalho cadastrado");
			return;
		}

		// Optional<ModelHorario> horarioEmAberto = serviceBaterPonto.findPontoAberto(idPonto);

		// if (horarioEmAberto.isPresent() && isSaida) {
		// serviceBaterPonto.fecharPonto(horarioEmAberto.get(), horario);
		// response.setStatus(200);
		// response.getWriter().append("Horario batido com sucesso!");

		// } else if(horarioEmAberto.isEmpty()) {
		ModelHorario pontoCriado = serviceBaterPonto.baterPonto(entrada, saida);
		servicoHoras.calcularExtra(pontoCriado);
		servicoHoras.calcularAtraso(pontoCriado);
		
		response.setStatus(200);
		response.getWriter().append("Novo Ponto Criado!");

	}
}
