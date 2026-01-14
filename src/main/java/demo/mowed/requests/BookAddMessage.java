package demo.mowed.requests;

import demo.mowed.core.MessageType;

public class BookAddMessage extends RequestMessage {
    private BookAddDto body;

    public BookAddMessage() {
    }

    public BookAddMessage(AuthRequest authRequest, BookAddDto dto) {
        super(MessageType.POST_ADD_BOOK, authRequest);
        this.body = dto;
    }

    public BookAddDto getBody() {
        return this.body;
    }

    public void setBody(BookAddDto value) {
        this.body = value;
    }
}
