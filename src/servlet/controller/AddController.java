package servlet.controller;


import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddController extends AbstractController {

    public AddController(JSONObject params, Object id) {
        super(params, id);
    }

    @Override
    protected Result do_handle(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        int x = this.params.getInt("x");
        int y = this.params.getInt("y");
        int sum = x + y;
        return new Result(sum, id);
    }
}
