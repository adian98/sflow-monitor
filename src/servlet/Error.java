package servlet;

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
}
