package servlet;

import config.Config;
import counterrecord.VirtDiskIoInfo;
import net.sf.json.JSONObject;
import servlet.controller.*;

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
            Error.invalidJson(req, resp);
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
            Config.LOG_ERROR("invalid request error %s", e.getMessage());
            Error.invalidRequest(req, resp);
            return;
        }

        req.setAttribute("id", id);

        AbstractController controller = null;
        switch (method) {
            case "add":
                controller = new AddController(params, id);
                break;
            case "host.description":
                controller = new HostDescriptionController(params, id);
                break;
            case "host.node":
                controller = new HostNodeController(params, id);
                break;
            case "host.cpu":
                controller = new HostCpuController(params, id);
                break;
            case "host.disk":
                controller = new HostDiskController(params, id);
                break;
            case "host.memory":
                controller = new HostMemoryController(params, id);
                break;
            case "host.net":
                controller = new HostNetIoController(params, id);
                break;
            case "virt.description":
                controller = new VirtDescriptionController(params, id);
                break;
            case "virt.cpu":
                controller = new VirtCpuController(params, id);
                break;
            case "virt.disk":
                controller = new VirtDiskController(params, id);
                break;
            default: {
                Config.LOG_ERROR("invalid mothod");
                Error.invalidMethod(req, resp);
                return;
            }
        }

        try {
            controller.handle(req, resp);
        } catch (Exception e) {
            Config.LOG_ERROR("controller handler error %s", e.getMessage());
            req.getRequestDispatcher("server_error.jsp").forward(req, resp);
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
