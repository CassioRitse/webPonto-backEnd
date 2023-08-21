package br.com.apiPonto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.com.apiPonto.model.ModelHorario;
import br.com.apiPonto.service.ServiceSaldoHorario;

@WebServlet(urlPatterns = "/horasAtrasadas")
public class ControllerHorasAtrasadas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServiceSaldoHorario serviceSaldoHorario = new ServiceSaldoHorario();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonArray jsonArray = new JsonArray();
        for (ModelHorario horario : serviceSaldoHorario.getHorasAtrasadas()){
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

}
