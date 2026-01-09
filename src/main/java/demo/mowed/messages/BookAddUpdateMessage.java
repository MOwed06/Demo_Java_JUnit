package demo.mowed.messages;

import demo.mowed.models.AuthRequestDto;
import demo.mowed.models.BookAddUpdateDto;

public class BookAddUpdateMessage extends RequestMessage {
    private BookAddUpdateDto addUpdateDto;

    public BookAddUpdateMessage() {
    }

    public BookAddUpdateMessage(MessageType messageType, AuthRequestDto authRequestDto, BookAddUpdateDto dto) {
        super(messageType, authRequestDto);
        this.addUpdateDto = dto;
    }

    public BookAddUpdateDto getAddUpdateDto() {
        return this.addUpdateDto;
    }

    public void setAddUpdateDto(BookAddUpdateDto value) {
        this.addUpdateDto = value;
    }
}
