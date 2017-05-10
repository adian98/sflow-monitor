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
    protected Result result;
    protected boolean is_commited;


    protected boolean isCommited() {
        return is_commited;
    }

    protected void setCommited() {
        is_commited = true;
    }

    public AbstractController(Object params, String id) {
        this.params = params;
        this.id = id;
        is_commited = false;
    }

    protected Result doHandle(HttpServletRequest req, HttpServletResponse resp)
            throws Exception{
        Config.LOG_ERROR("not implement");
        return null;
    }

    public void handle(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String jsp_path = null;
        try {
            result = doHandle(req, resp);
            if (isCommited()) {
                return;
            }

            req.setAttribute("result", result);
            jsp_path = "result.jsp";
;
        } catch (Exception e) {
            e.printStackTrace();
            Config.LOG_ERROR("handl req error %s", e.getMessage());
            jsp_path = "server_error.jsp";
        }

        RequestDispatcher rd = req.getRequestDispatcher(jsp_path);
        rd.forward(req, resp);
    }

}
