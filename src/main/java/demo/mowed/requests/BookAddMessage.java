package demo.mowed.requests;

import demo.mowed.core.MessageType;

public class BookAddMessage extends RequestMessage {
    private BookAddDto addDto;

    public BookAddMessage() {
    }

    public BookAddMessage(AuthRequest authRequest, BookAddDto dto) {
        super(MessageType.POST_ADD_BOOK, authRequest);
        this.addDto = dto;
    }

    public BookAddDto getAddUpdateDto() {
        return this.addDto;
    }

    public void setAddUpdateDto(BookAddDto value) {
        this.addDto = value;
    }
}
