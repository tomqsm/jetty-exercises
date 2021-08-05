package biz.lwb.groovysetup.servlet

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletException
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@WebServlet(name = "shopone", value="ss")
public class ShopServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(ShopServlet.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.writer.println("shopone response")
    }

    @Override

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        def reader = req.getReader()
        objectMapper.readValue(reader, Map.class)
    }
}
