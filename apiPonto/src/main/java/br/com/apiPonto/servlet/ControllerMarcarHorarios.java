package br.com.apiPonto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalTime;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.apiPonto.model.ModelHorario;
import br.com.apiPonto.service.ServiceMarcarHoraio;

@WebServlet(urlPatterns = "/marcarHorarios")
public class ControllerMarcarHorarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServiceMarcarHoraio serviceMarcarHoraio = new ServiceMarcarHoraio();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JsonArray jsonArray = new JsonArray();
		for (ModelHorario horario : serviceMarcarHoraio.getAllHorariosTrabalho()) {
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
		serviceMarcarHoraio.removeAllHorariosDeTrabalho();

		response.setContentType("application/json");
		response.setStatus(200);
		response.getWriter().append("Apagou Com Sucesso!");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		StringBuilder jsonBuilder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			jsonBuilder.append(line);
		}

		JsonObject json = JsonParser.parseString(jsonBuilder.toString()).getAsJsonObject();
		LocalTime entrada = LocalTime.parse(json.get("entrada").getAsString());
		LocalTime saida = LocalTime.parse(json.get("saida").getAsString());

		if (!serviceMarcarHoraio.isCheioHorario()) {
			serviceMarcarHoraio.addHorarioDeTrabalho(entrada, saida);
			response.setStatus(200);
			response.getWriter().append("Horario Adicionado com sucesso!");
		} else {
			response.setStatus(406);
			response.getWriter().append("Horario cheios, no maximo 3 horarios cadastrados por funcionario!");

		}
	}

}
