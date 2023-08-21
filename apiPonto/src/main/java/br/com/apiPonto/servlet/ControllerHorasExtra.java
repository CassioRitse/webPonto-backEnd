package br.com.apiPonto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.apiPonto.model.ModelHorario;
import br.com.apiPonto.service.ServiceSaldoHorario;

@WebServlet(urlPatterns = "/horasExtra")
public class ControllerHorasExtra extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServiceSaldoHorario serviceSaldoHorario = new ServiceSaldoHorario();

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonArray jsonArray = new JsonArray();
		for (ModelHorario horario : serviceSaldoHorario.getHorasExtras()) {
			JsonObject jsonHorario = new JsonObject();
			jsonHorario.addProperty("idhorario", horario.getIdhorario().toString());
			jsonHorario.addProperty("entrada", horario.getEntrada().toString());
			jsonHorario.addProperty("saida", horario.getSaida().toString());
			jsonArray.add(jsonHorario);
		}
		// Envia resposta para o cliente
				response.setContentType("application/json");
				response.setStatus(200);
				response.getWriter().append(jsonArray.toString());
	}
	
	
	//Estive fazendo esse POST pq gostaria de trabalhar com a ideia de fazer o Post de Horas Extras
	// como base no envio dos pontos do funciona e suas horas trabalhas
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JsonArray jsonArray = new JsonArray();

		// Le o corpo da requisição
		BufferedReader reader = request.getReader();
		StringBuilder jsonBuilder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			jsonBuilder.append(line);
		}

		// Converte o corpo da requisição para JSON
		JsonObject requestBody = JsonParser.parseString(jsonBuilder.toString()).getAsJsonObject();
		JsonArray horariosJsonArray = requestBody.getAsJsonArray("horarios");
		JsonObject pontoJsonObject = requestBody.getAsJsonObject("ponto");

		// Converte os JSON arrays para listas de ModelHorario
		List<ModelHorario> horarios = new ArrayList<>();
		for (JsonElement horarioJson : horariosJsonArray) {
			JsonObject horarioObj = horarioJson.getAsJsonObject();
			LocalTime entrada = LocalTime.parse(horarioObj.get("entrada").getAsString());
			LocalTime saida = LocalTime.parse(horarioObj.get("saida").getAsString());
			horarios.add(new ModelHorario(entrada, saida));
		}

		LocalTime pontoEntrada = LocalTime.parse(pontoJsonObject.get("entrada").getAsString());
		LocalTime pontoSaida = LocalTime.parse(pontoJsonObject.get("saida").getAsString());
		ModelHorario ponto = new ModelHorario(pontoEntrada, pontoSaida);

		// Chama o serviço para calcular as horas extras
		List<ModelHorario> listHorariosExtras = serviceSaldoHorario.calcularExtra(ponto);

		// Converte os resultados para JSON
		for (ModelHorario horario : listHorariosExtras) {
			JsonObject jsonHorario = new JsonObject();
			jsonHorario.addProperty("idhorario", horario.getIdhorario().toString());
			jsonHorario.addProperty("entrada", horario.getEntrada().toString());
			jsonHorario.addProperty("saida", horario.getSaida().toString());
			jsonArray.add(jsonHorario);
		}

		// Envia resposta para o cliente
		response.setContentType("application/json");
		response.setStatus(200);
		response.getWriter().append(jsonArray.toString());
	}

}
