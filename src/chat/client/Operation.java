package chat.client;

public enum Operation {
    LOGIN,
    INFO,
    ENTER,
    EXIT;

    public static Operation getAllowableOperationByOrdinal(Integer i) {
        if (i == 0) {
            throw new IllegalArgumentException();
        } else if (i == 1) {
            return INFO;
        } else if (i == 2) {
            return ENTER;
        } else if (i == 4) {
            return EXIT;
        } else {
            throw new IllegalArgumentException();
        }
    }

}