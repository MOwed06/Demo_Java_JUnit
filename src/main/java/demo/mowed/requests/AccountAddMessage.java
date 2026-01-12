package demo.mowed.requests;

import demo.mowed.core.MessageType;
import lombok.*;

public class AccountAddMessage extends RequestMessage {
    @Getter
    @Setter
    private AccountAddDto addDto;

    public AccountAddMessage() {
    }

    public AccountAddMessage(AuthRequest authRequest, AccountAddDto dto) {
        super(MessageType.POST_ADD_ACCOUNT, authRequest);
        this.addDto = dto;
    }
}
