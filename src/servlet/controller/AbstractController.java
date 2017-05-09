package servlet.controller;


import config.Config;
import net.sf.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AbstractController {
    protected JSONObject params;
    protected Object id;
    Result result;

    public AbstractController(JSONObject params, Object id) {
        this.params = params;
        this.id = id;
    }

    protected Result do_handle(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        Config.LOG_ERROR("not implement");
        return null;
    }

    public void handle(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        try {
            this.do_handle(req, resp);
            result = do_handle(req, resp);
            req.setAttribute("result", result);
            RequestDispatcher rd = req.getRequestDispatcher("result.jsp");
            rd.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
