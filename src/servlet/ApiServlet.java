package servlet;

import config.Config;
import net.sf.json.JSONObject;
import servlet.controller.AbstractController;

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
        JSONObject params;
        try {
            method = body.getString("method");
            id = body.getString("id");
            params = body.getJSONObject("params");
        } catch (Exception e) {
            Config.LOG_ERROR("get method error %s", e.getMessage());
            RequestDispatcher rd = req.getRequestDispatcher("invalid_json.jsp");
            rd.forward(req, resp);
            return;
        }

        AbstractController controller;
        switch (method) {
            case "add":
                return;


            default: {
                Config.LOG_ERROR("invalid ");
            }
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
