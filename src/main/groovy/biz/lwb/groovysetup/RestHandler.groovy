package biz.lwb.groovysetup

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.eclipse.jetty.server.Request
import org.eclipse.jetty.server.handler.AbstractHandler

class RestHandler extends AbstractHandler {

    public static final String PATH = "/api"

    @Override
    void handle(String target, Request baseRequest, HttpServletRequest request,
                       HttpServletResponse response) {
        response.addHeader("Content-Type", "application/json")
        response.writer.write(
                """
                    {"name": "Tomasz","surname": "Kusmierczyk","path":"${request.getContextPath()}","method":"${request.getMethod()}"}
                   """
        )
        baseRequest.setHandled(true);
    }
}
