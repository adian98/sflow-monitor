package servlet.controller;


public class Result {
    private Object result;
    private Object id;

    public Result(Object result, Object id) {
        this.result = result;
        this.id = id;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getResult() {
        return result;
    }

    public Object getId() {
        return id;
    }
}
