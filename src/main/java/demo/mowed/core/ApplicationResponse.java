package demo.mowed.core;

public record ApplicationResponse (boolean remainActive, String statusMessage) {

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ApplicationResponse) {
            ApplicationResponse compare = (ApplicationResponse)obj;
            return (compare.statusMessage.equals(this.statusMessage)
            && (compare.remainActive == this.remainActive));
        }
        return false;
    }
}
