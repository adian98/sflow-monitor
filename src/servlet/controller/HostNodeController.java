package servlet.controller;


import counterrecord.HostDescription;
import counterrecord.HostNetIoInfo;
import counterrecord.HostNodeInfo;
import counterrecord.Utils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import servlet.Error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

public class HostNodeController extends AbstractController{
    public HostNodeController(Object params, String id) {
        super(params, id);
    }

    @Override
    protected Result doHandle(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        JSONObject jsonObject = JSONObject.fromObject(params);
        if (!jsonObject.has("ip")) {
            Error.invalidRequest(req, resp);
            setCommited();
            return null;
        }

        String host_ip = jsonObject.getString("ip");

        Long timestamp;
        if (jsonObject.has("timestamp")) {
            timestamp = jsonObject.getLong("timestamp");
        } else {
            timestamp = Utils.timeNow();
        }

        List<HashMap> list = HostNodeInfo.fromDb(host_ip, timestamp);
        JSONArray jsonArray = JSONArray.fromObject(list);
        return new Result(jsonArray.toString(2), id);
    }
}
