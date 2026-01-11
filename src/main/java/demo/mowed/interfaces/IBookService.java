package demo.mowed.interfaces;

import demo.mowed.requests.GetMessage;
import demo.mowed.responses.BookDetailsRecord;
import demo.mowed.responses.BookOverviewRecord;

import java.util.List;

public interface IBookService {
    BookDetailsRecord getBook(GetMessage request);

    List<BookOverviewRecord> getBooksByGenre(GetMessage request);
}
