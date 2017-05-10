package servlet.controller;

import counter_record.HostDescription;
import net.sf.json.JSONArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
