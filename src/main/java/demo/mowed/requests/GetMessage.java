package demo.mowed.requests;

import demo.mowed.core.MessageType;

/*
GetMessage is overloaded by design
The MessageType parameter in combination with queryParameters dictates behavior
GetMessage is generalized object to model get requests of _any_ flavor
Some messages will possess queryParameters with int value, others with String, some a null queryParameter
For example:
- GET_AUTHOR_LIST will have queryParameters = null
- GET_BOOK will have int queryParameter, ex. https://localhost:7119/api/books/2
- GET_BOOKS_BY_AUTHOR will have String queryParameter, ex. https://localhost:7119/api/books/author?name=collins
 */
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
