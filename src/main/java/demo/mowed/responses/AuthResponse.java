package demo.mowed.responses;

/*
Authorization Response
Authorization failures will be associated with an exception (refer to AuthService.java).
Users with isActive false can view books, but not purchase books.
Only user with isAdmin true may add/modify users and books.
 */
public record AuthResponse(boolean isActive, boolean isAdmin) {

    @Override
    public boolean equals(Object obj) {
        // if compare object is same type
        // and parameters are equal
        // then record is equal
        if (obj instanceof AuthResponse){
            AuthResponse compare = (AuthResponse)obj;
            return (compare.isActive == this.isActive)
                    && (compare.isAdmin == this.isAdmin);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("isActive: %b, isAdmin: %b", isActive, isAdmin);
    }
}
