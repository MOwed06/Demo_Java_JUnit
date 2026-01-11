package demo.mowed.requests;

public class QueryParameters {
    private Integer queryInt;
    private String queryString;

    public QueryParameters() {
    }

    public QueryParameters(Integer queryInt) {
        this.queryInt = queryInt;
    }

    public QueryParameters(String queryString) {
        this.queryString = queryString;
    }

    public Integer getQueryInt() {
        return this.queryInt;
    }

    public void setQueryInt(Integer value) {
        this.queryInt = value;
    }

    public String getQueryString() {
        return this.queryString;
    }

    public void setQueryString(String value) {
        this.queryString = value;
    }
}
