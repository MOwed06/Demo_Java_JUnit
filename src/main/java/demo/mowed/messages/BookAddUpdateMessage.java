package demo.mowed.messages;

public class BookAddUpdateMessage extends RequestMessage {
    private BookAddUpdateDto addUpdateDto;

    public BookAddUpdateMessage() {
    }

    public BookAddUpdateMessage(MessageType messageType, AuthRequest authRequest, BookAddUpdateDto dto) {
        super(messageType, authRequest);
        this.addUpdateDto = dto;
    }

    public BookAddUpdateDto getAddUpdateDto() {
        return this.addUpdateDto;
    }

    public void setAddUpdateDto(BookAddUpdateDto value) {
        this.addUpdateDto = value;
    }
}
