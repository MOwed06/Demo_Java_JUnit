package demo.mowed.core;



public class ApplicationRunner {

    public ApplicationResponse ProcessRequest(String userEntry) {
        var userSelection = userEntry.toUpperCase();

        switch (userSelection) {
            case "Q":
                return new ApplicationResponse("done", false);
            case "B":
                throw new BookException("this is iffy");
            default:
                return new ApplicationResponse("happy", true);
        }
    }


}
