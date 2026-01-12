package demo.mowed.core;


/*
MessageType links listed below show equivalent operation in the "BigBooks" C# webapi
Refer to: https://github.com/MOwed06/Demo_CSharp_API
*/
public enum MessageType {
    GET_BOOK, // ex: https://localhost:7119/api/books/2
    GET_BOOKS_BY_GENRE, // ex: https://localhost:7119/api/books/genre?name=childrens
    GET_BOOKS_BY_AUTHOR, // ex: https://localhost:7119/api/books/author?name=collins
    GET_BOOK_REVIEWS, // ex: https://localhost:7119/api/books/6/reviews
    GET_AUTHOR_LIST, // ex: https://localhost:7119/api/books/authorlist
    POST_ADD_BOOK, // ex: https://localhost:7119/api/books
    POST_ADD_BOOK_REVIEW, // ex: https://localhost:7119/api/books/5/reviews
    PATCH_UPDATE_BOOK, // ex: https://localhost:7119/api/books/7,
    GET_ACCOUNT, // ex: https://localhost:7119/api/accounts/22
    GET_ACCOUNT_LIST, // ex: https://localhost:7119/api/accounts/list
    GET_CURRENT_USER, // ex: https://localhost:7119/api/users
    POST_ADD_ACCOUNT, // ex: https://localhost:7119/api/accounts
    PATCH_UPDATE_ACCOUNT, // ex: https://localhost:7119/api/accounts/7
    POST_USER_DEPOST, // ex: https://localhost:7119/api/transactions/deposit
    POST_PURCHASE_BOOK // ex: https://localhost:7119/api/transactions/purchase
}
