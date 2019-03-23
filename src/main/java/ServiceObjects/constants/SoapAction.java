package ServiceObjects.constants;

public enum SoapAction {
    CHECK_TEXTS("checkTexts", "CheckTextsRequest");
    String method;
    String reqName;

    public String getMethod() {
        return method;
    }

    public String getReqName() {
        return reqName;
    }

    private SoapAction(String method, String reqName) {
        this.method = method;
        this.reqName = reqName;
    }
}
