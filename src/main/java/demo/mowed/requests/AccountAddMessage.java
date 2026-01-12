package demo.mowed.requests;

import lombok.*;

public class AccountAddMessage extends RequestMessage {
    @Getter
    @Setter
    private AccountAddDto addDto;

    public AccountAddMessage(AuthRequest authRequest, AccountAddDto dto) {
        super(MessageType.POST_ADD_BOOK, authRequest);
        this.addDto = dto;
    }
}
