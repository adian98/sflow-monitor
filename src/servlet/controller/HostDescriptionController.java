package servlet.controller;

import counterrecord.HostDescription;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class HostDescriptionController extends AbstractController {
    public HostDescriptionController(Object params, String id) {
        super(params, id);
    }

    @Override
    protected Result doHandle(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        List<HashMap> list = HostDescription.fromDb();
        JSONArray jsonArray = JSONArray.fromObject(list);
        return new Result(jsonArray.toString(2), id);
    }
}
