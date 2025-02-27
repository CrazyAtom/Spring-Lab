package lab.servlet.web.frontcontroller.v5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lab.servlet.web.frontcontroller.ModelView;
import lab.servlet.web.frontcontroller.MyView;
import lab.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import lab.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import lab.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import lab.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

	private final Map<String, Object> handlerMappingMap = new HashMap<>();
	private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

	public FrontControllerServletV5() {
		initHandlerMappingMap();
		initHandlerAdapters();
	}

	private void initHandlerMappingMap() {
		handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
		handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
		handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
	}

	private void initHandlerAdapters() {
		handlerAdapters.add(new ControllerV3HandlerAdapter());
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws
		ServletException,
		IOException {

		Object handler = getHandler(request);
		if (handler == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		MyHandlerAdapter adapter = getHandlerAdapter(handler);
		ModelView modelView = adapter.handle(request, response, handler);

		MyView view = viewResolver(modelView.getViewName());
		view.render(modelView.getModel(), request, response);
	}

	private Object getHandler(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		return handlerMappingMap.get(requestURI);
	}

	private MyHandlerAdapter getHandlerAdapter(Object handler) {
		return handlerAdapters.stream()
			.filter(handlerAdapter -> handlerAdapter.supports(handler))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("handler adapter not found. handler=" + handler));
	}

	private MyView viewResolver(String viewName) {
		return new MyView("/WEB-INF/views/" + viewName + ".jsp");
	}
}
