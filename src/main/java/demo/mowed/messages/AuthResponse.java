package demo.mowed.messages;

public record AuthResponse(boolean isActive, boolean isAdmin) {

    @Override
    public boolean equals(Object obj) {
        // if compare object is same type
        // and parameters are equal
        // then record is equal
        if (obj instanceof AuthResponse(boolean authorized, boolean admin)){
            return (authorized == this.isActive)
                    && (admin == this.isAdmin);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("isActive: %b, isAdmin: %b", isActive, isAdmin);
    }
}
