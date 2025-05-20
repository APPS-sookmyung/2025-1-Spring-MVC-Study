package hello.servlet.web.frontcontroller.v1.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.v1.ControllerV1;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

// 회원 목록 조회 컨트롤러 (v1 버전)
public class MemberListControllerV1 implements ControllerV1 {

    // 회원 정보를 저장하고 있는 싱글톤 저장소 인스턴스를 가져옴
    private MemberRepository memberRepository = MemberRepository.getInstance();

    // ControllerV1 인터페이스의 메서드 구현
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 저장소에서 회원 목록 전체를 조회
        List<Member> members = memberRepository.findAll();

        // 2. 요청 객체에 "members"라는 이름으로 회원 리스트를 저장 (JSP에서 사용 가능하도록)
        request.setAttribute("members", members);

        // 3. 뷰로 이동할 경로 설정 (JSP 파일 위치)
        String viewPath = "/WEB-INF/views/members.jsp";

        // 4. JSP로 포워딩 (forward)
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);  // JSP에게 request/response 넘김
    }
}
