package servlet.controller;


import config.Config;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AbstractController {
    protected JSONObject param;

    public AbstractController(JSONObject param) {
        this.param = param;
    }

    public void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Config.LOG_ERROR("not implement");
    }

}
