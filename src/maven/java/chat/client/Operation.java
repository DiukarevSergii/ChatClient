package chat.client;

public enum Operation {
    LOGIN,
    INFO,
    PUBLIC_MESSAGE,
    PRIVATE_MESSAGE,
    STATUS,
    EXIT;

    public static Operation getAllowableOperationByOrdinal(Integer i) {
        if (i == 0) {
            throw new IllegalArgumentException();
        } else if (i == 1) {
            return INFO;
        } else if (i == 2) {
            return PUBLIC_MESSAGE;
        } else if (i == 3) {
            return PRIVATE_MESSAGE;
        }else if (i == 4) {
            return STATUS;
        } else if (i == 5) {
            return EXIT;
        } else {
            throw new IllegalArgumentException();
        }
    }

}