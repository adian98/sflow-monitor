package servlet.controller;

import servlet.Error;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorController extends AbstractController {
    private int id;
    private int code;
    private String message;

    public ErrorController(int id, int code) {
        super(null);

        this.id = id;
        this.code = code;
        this.message = Error.errorCodeToString(code);
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        req.setAttribute("error", this);
        RequestDispatcher rd = req.getRequestDispatcher("error.jsp");
        rd.forward(req, resp);
    }
}
