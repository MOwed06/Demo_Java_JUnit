package demo.mowed.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import demo.mowed.core.MessageType;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestMessage {
    @Getter
    @Setter
    private MessageType messageType;

    @Getter
    @Setter
    private AuthRequest authRequest;

    public RequestMessage() {
    }

    public RequestMessage(MessageType messageType, AuthRequest authRequest) {
        this.messageType = messageType;
        this.authRequest = authRequest;
    }
}
