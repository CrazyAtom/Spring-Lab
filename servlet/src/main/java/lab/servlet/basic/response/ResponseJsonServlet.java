package lab.servlet.basic.response;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lab.servlet.basic.HelloData;

@WebServlet(name = "responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws
		ServletException,
		IOException {
		// Content-Type: application/json
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");

		HelloData helloData = new HelloData();
		helloData.setUsername("kim");
		helloData.setAge(20);

		String result = objectMapper.writeValueAsString(helloData);
		response.getWriter().write(result);
	}
}
