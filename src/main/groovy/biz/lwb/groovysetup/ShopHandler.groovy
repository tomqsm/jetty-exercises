package biz.lwb.groovysetup

import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.eclipse.jetty.server.Request
import org.eclipse.jetty.server.handler.AbstractHandler

class ShopHandler extends AbstractHandler {

    public static final String PATH = "/shop"


    @Override
    void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.addHeader("Content-Type", "application/json")
        response.setStatus(HttpServletResponse.SC_OK)
        response.writer.write(
                '''
                    {"name": "Tomasz",
                    "surname": "Kusmierczyk"}
                   '''
        )
        baseRequest.setHandled(true);
    }
}
