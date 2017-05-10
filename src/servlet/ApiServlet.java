package servlet;

import config.Config;
import net.sf.json.JSONObject;
import servlet.controller.AbstractController;
import servlet.controller.AddController;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.*;


@WebServlet(name = "ApiServlet", urlPatterns = "/api")
public class ApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json-rpc");

        JSONObject body;
        try {
            body = getBody(req);
        } catch (Exception e) {
            Config.LOG_ERROR("get body error %s", e.getMessage());
            RequestDispatcher rd = req.getRequestDispatcher("invalid_json.jsp");
            rd.forward(req, resp);
            return;
        }
        String method;
        String id = null;
        Object params;
        try {
            method = body.getString("method");
            id = body.getString("id");
            params = body.get("params");
        } catch (Exception e) {
            Config.LOG_ERROR("get method error %s", e.getMessage());
            RequestDispatcher rd = req.getRequestDispatcher("invalid_request.jsp");
            rd.forward(req, resp);
            return;
        }

        AbstractController controller = null;
        switch (method) {
            case "add":
                controller = new AddController(params, id);
                break;


            default: {
                Config.LOG_ERROR("invalid ");
                req.getRequestDispatcher("invalid_method.jsp").forward(req, resp);
                return;
            }
        }

        try {
            controller.handle(req, resp);
        } catch (Exception e) {
            Config.LOG_ERROR("controller handler error %s", e.getMessage());
            //req.setAttribute("id", id);
            req.getRequestDispatcher("invalid_method.jsp").forward(req, resp);
        }
    }



    private JSONObject getBody(HttpServletRequest req)
            throws ServletException, IOException {
        InputStream stream = req.getInputStream();
        byte[] bytes = new byte[2048];
        StringBuilder out = new StringBuilder();

        for (int n; (n = stream.read(bytes)) != -1; ) {
            out.append(new String(bytes,0, n));
        }
        String body = out.toString();
        Config.LOG_INFO("body = %s", body);
        return JSONObject.fromObject(body);
    }
}
