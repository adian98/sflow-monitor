package servlet.controller;

import counter_record.Utils;
import counter_record.VirtNetIoInfo;
import db.DB;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import servlet.Error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VirtNetController extends AbstractController {
    public VirtNetController(Object params, String id) {
        super(params, id);
    }

    @Override
    protected Result doHandle(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (params == null) {
            Error.invalidRequest(req, resp);
            setCommited();
            return null;
        }

        JSONObject jsonObject = JSONObject.fromObject(params);
        if (jsonObject == null || !jsonObject.has("hostname")) {
            Error.invalidRequest(req, resp);
            setCommited();
            return null;
        }

        String hostname = jsonObject.getString("hostname");

        Long timestamp;
        if (jsonObject.has("timestamp")) {
            timestamp = jsonObject.getLong("timestamp");
        } else {
            timestamp = Utils.timeNow();
        }

        List<HashMap> list = new ArrayList<HashMap>();
        synchronized (DB.db_lock) {
            VirtNetIoInfo.fromDb(hostname, timestamp, list);
        }
        JSONArray jsonArray = JSONArray.fromObject(list);
        return new Result(jsonArray.toString(2), id);
    }
}
