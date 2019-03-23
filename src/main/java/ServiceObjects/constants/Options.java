package ServiceObjects.constants;

public enum Options {
    WHITOUT_OPTIONS(0),
    IGNORE_DIGITS(2),
    IGNORE_URLS(4),
    FIND_REPEAT_WORDS(8),
    IGNORE_CAPITALIZATION(512);

    public int code;

    private Options(int code) {
        this.code = code;
    }

}
