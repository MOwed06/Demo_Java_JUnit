package demo.mowed.messages;

import demo.mowed.models.AuthRequestDto;
import demo.mowed.models.QueryParameters;

public class GetMessage extends RequestMessage {
    private QueryParameters queryParameters;

    public GetMessage() {
    }

    public GetMessage(MessageType messageType, AuthRequestDto authRequestDto, QueryParameters requestDto) {
        super(messageType, authRequestDto);
        this.queryParameters = requestDto;
    }

    public QueryParameters getQueryParameters() {
        return this.queryParameters;
    }

    public void setQueryParameters(QueryParameters value) {
        this.queryParameters = value;
    }
}
