package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Error {
    public static final int PARSE_ERROR = -32700;
    public static final int INVALID_REQUEST = -32600;
    public static final int METHOD_NOT_FOUND = -32601;
    public static final int INVALID_PARAMS = -32602;
    public static final int INTERNAL_ERROR = -32603;
    public static final int OTHER_ERROR = -32000;

    public static String errorCodeToString(int code) {
        switch (code) {
            case PARSE_ERROR:
                return "Parse error";
            case INVALID_REQUEST:
                return "Invalid Request";
            case METHOD_NOT_FOUND:
                return "Method not found";
            case INVALID_PARAMS:
                return "Invalid params";
            case INTERNAL_ERROR:
                return "Internal error";
            default:
                return "Other error";
        }
    }

    static public void invalidMethod(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        req.getRequestDispatcher("invalid_method.jsp").forward(req, resp);
    }

    static public void invalidRequest(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        req.getRequestDispatcher("invalid_request.jsp").forward(req, resp);
    }


    static public void invalidJson(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        req.getRequestDispatcher("invalid_json.jsp").forward(req, resp);
    }



}
