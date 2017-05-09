package servlet;

import config.Config;
import net.sf.json.JSONObject;
import servlet.controller.AbstractController;
import servlet.controller.ErrorController;

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
        String method;
        int id;
        JSONObject params;
        try {
            body = getBody(req);
            id = body.getInt("id");
            method = body.getString("method");
            params = body.getJSONObject("params");
        } catch (Exception e) {
            Config.LOG_ERROR(e.getMessage());
            new ErrorController(-1, Error.INVALID_PARAMS).handle(req, resp);
            return;
        }

        AbstractController controller;
        switch (method) {


            default: {
                new ErrorController(id, Error.INVALID_PARAMS).handle(req, resp);
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
