package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// @WebServlet: 이 서블릿을 /front-controller/v1/* 경로로 매핑한다.
@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    // URL 요청 경로와 해당 컨트롤러 객체를 연결하는 Map
    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    // 생성자에서 경로별로 어떤 컨트롤러를 호출할지 미리 매핑해둔다.
    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    // HTTP 요청이 들어올 때 실행되는 메서드
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("FrontControllerServletV1.service");

        // 요청된 URI를 꺼냄 (예: /front-controller/v1/members)
        String requestURI = request.getRequestURI();

        // URI에 맞는 컨트롤러를 맵에서 꺼냄
        ControllerV1 controller = controllerMap.get(requestURI);

        // 컨트롤러가 없으면 404 에러 반환
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 컨트롤러가 있으면 그 컨트롤러의 process() 메서드 호출
        controller.process(request, response);
    }
}
