package demo.mowed.requests;

import demo.mowed.core.MessageType;

public class AccountAddMessage extends RequestMessage {
    private AccountAddDto body;

    public AccountAddMessage() {
    }

    public AccountAddMessage(AuthRequest authRequest, AccountAddDto dto) {
        super(MessageType.POST_ADD_ACCOUNT, authRequest);
        this.body = dto;
    }

    public AccountAddDto getBody() {
        return body;
    }

    public void setBody(AccountAddDto body) {
        this.body = body;
    }
}
