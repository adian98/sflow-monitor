package servlet.controller;


import org.apache.commons.lang.StringUtils;

public class Result {
    private Object result;
    private String id;

    public Result(Object result, String id) {
        this.result = result;

        if (id == null || StringUtils.isNumeric(id)) {
            this.id = id;
        } else {
            //json str
            this.id = "\"" + id + "\"";
        }
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getResult() {
        return result;
    }

    public String getId() {
        return id;
    }
}
