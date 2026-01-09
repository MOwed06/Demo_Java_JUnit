package demo.mowed.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import demo.mowed.models.AuthRequestDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestMessage {
    private MessageType messageType;
    private AuthRequestDto authRequestDto;

    public RequestMessage() {
    }

    public RequestMessage(MessageType messageType, AuthRequestDto authRequestDto) {
        this.messageType = messageType;
        this.authRequestDto = authRequestDto;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public AuthRequestDto getAuthRequestDto() {
        return this.authRequestDto;
    }

    public void setAuthRequestDto(AuthRequestDto value) {
        this.authRequestDto = value;
    }
}
