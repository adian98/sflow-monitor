package servlet.controller;


import config.Config;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AbstractController {
    protected Object params;
    protected String id;
    Result result;

    public AbstractController(Object params, String id) {
        this.params = params;
        this.id = id;
    }

    protected Result doHandle(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        Config.LOG_ERROR("not implement");
        return null;
    }

    public void handle(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        try {
            result = doHandle(req, resp);
            req.setAttribute("result", result);
            RequestDispatcher rd = req.getRequestDispatcher("result.jsp");
            rd.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
