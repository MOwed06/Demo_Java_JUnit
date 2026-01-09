package demo.mowed.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.mowed.core.BookException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MessageParser {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public RequestMessage readMessageTypeDataFile(String fileName) throws IOException  {
        var fullName = String.format("data/%s", fileName);
        return OBJECT_MAPPER.readValue(new File(fullName), RequestMessage.class);
    }

    public <T> T readMessage(String fileName, Class<T> tClass) throws IOException {
        var fullName = String.format("data/%s", fileName);
        return OBJECT_MAPPER.readValue(new File(fullName), tClass);
    }

    // demo purposes only
    public static void main(String[] args) {
        try {
            var messageParser = new MessageParser();
            var observed = messageParser.readMessageTypeDataFile("GetBook17.json");
            System.out.println(observed.getMessageType());

            var obs2 = messageParser.readMessage("GetBook17.json", GetMessage.class);
            System.out.println(obs2.getQueryParameters().getQueryInt());

            var obs3 = messageParser.readMessage("GetBooksCollins.json", GetMessage.class);
            System.out.println(obs3.getMessageType());
            System.out.println(obs3.getQueryParameters().getQueryString());

            var obs4 = messageParser.readMessage("BookAddGreatContradiction.json", BookAddUpdateMessage.class);
            System.out.println(obs4.getMessageType());
            System.out.println(obs4.getAddUpdateDto().getTitle());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
