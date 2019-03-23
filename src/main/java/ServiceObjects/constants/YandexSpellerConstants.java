package ServiceObjects.constants;

public class YandexSpellerConstants {

    public static final String PARAM_TEXT = "text";
    public static final String PARAM_OPTIONS = "options";
    public static final String PARAM_LANG = "lang";
    public static final String QUOTES = "\"";

    public enum Language {
        RU("ru"),
        UK("uk"),
        EN("en");

        public String languageCode;

        private Language(String languageCode) {
            this.languageCode = languageCode;
        }

    }

    public enum ErrorCodes {
        ERROR_UNKNOWN_WORD("1"),
        ERROR_REPEAT_WORD("2"),
        ERROR_CAPITALIZATION("3"),
        ERROR_TOO_MANY_ERRORS("4");

        private String code;

        public String getCode() {
            return code;
        }

        private ErrorCodes(String code) {
            this.code = code;
        }

    }

}









