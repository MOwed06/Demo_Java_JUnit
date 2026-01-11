package demo.mowed.requests;

public class GetMessage extends RequestMessage {
    private QueryParameters queryParameters;

    public GetMessage() {
    }

    public GetMessage(MessageType messageType, AuthRequest authRequest, QueryParameters requestDto) {
        super(messageType, authRequest);
        this.queryParameters = requestDto;
    }

    public QueryParameters getQueryParameters() {
        return this.queryParameters;
    }

    public void setQueryParameters(QueryParameters value) {
        this.queryParameters = value;
    }
}
