package demo.mowed.models;

public record AuthResponse(boolean isAuthorized, boolean isAdmin) {

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AuthResponse(boolean authorized, boolean admin)){
            return (authorized == this.isAuthorized)
                    && (admin == this.isAdmin);
        }
        return false;
    }
}
