package servlet.controller;


import log.LOG;
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

    public AbstractController(Object params, String id) {
        this.params = params;
        this.id = id;
        is_commited = false;
    }

    protected Result doHandle(HttpServletRequest req, HttpServletResponse resp)
            throws Exception{
        LOG.ERROR("not implement");
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
        } catch (Exception e) {
            e.printStackTrace();
            LOG.ERROR("handl req error %s", e.getMessage());
            jsp_path = "server_error.jsp";
        }

        RequestDispatcher rd = req.getRequestDispatcher(jsp_path);
        rd.forward(req, resp);
    }


    protected boolean isCommited() {
        return is_commited;
    }

    protected void setCommited() {
        is_commited = true;
    }

}
