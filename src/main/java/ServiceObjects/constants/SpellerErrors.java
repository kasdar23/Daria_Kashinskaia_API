package ServiceObjects.constants;

public enum SpellerErrors {
    ERROR_UNKNOWN_WORD(1),
    ERROR_REPEAT_WORD(2),
    ERROR_CAPITALIZATION(3),
    ERROR_TOO_MANY_ERRORS(4);

    public int code;

    private SpellerErrors(int code) {
        this.code = code;
    }
}
