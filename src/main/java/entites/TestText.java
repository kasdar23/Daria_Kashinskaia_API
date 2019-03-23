package entites;

public enum TestText {
    TEXT_MISSING_LETTERS_RU("Я не люблю картшку", "картошку"),
    TEXT_MISSING_LETTERS_EN("I don't like ptato.", "potato"),
    TEXT_MISSING_LETTERS_UK("Я не люблю ккартоплю", "картоплю"),
    TEXT_CAPITAL_LETTERS_RU("Я собираюсь в кИЕВ.", "Киев"),
    TEXT_CAPITAL_LETTERS_EN("I am going to kIEV", "Kiev"),
    TEXT_CAPITAL_LETTERS_UK("Я збираюся до кИєВа", "Києва"),
    TEXT_REPEAT_WORDS_RU("Машина ехала ехала быстрее", "ехала"),
    TEXT_REPEAT_WORDS_EN("The car went went faster", "went"),
    TEXT_REPEAT_WORDS_UK("Машина їхала їхала швидше", "їхала"),
    TEXT_WITH_URLS("my mail: ptato@yandex.ru", ""),
    TEXT_WRONG_CONTEXT("скучать музыку", "скачать"),
    TEXT_FOR_TWO_OPTIONS("I am going going tto kIEV",""),
    TEXT_WITH_DIGITS("картошка7", "");

    public String blockWrong;
    public String blockCorr;

    TestText(String blockWrong, String blockCorr) {
        this.blockWrong = blockWrong;
        this.blockCorr = blockCorr;
    }
}
