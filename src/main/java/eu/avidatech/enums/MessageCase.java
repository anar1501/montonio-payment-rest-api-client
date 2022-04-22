package eu.avidatech.enums;

public enum MessageCase {
    ONLY_NUMBER_ACCEPTED("Only number accepted!"),
    ONLY_USD_AND_EUR("Only EUR or USD accepted!"),
    WRONG_FORMAT_EMAIL("Email is incorrect!"),
    WRONG_FORMAT_CURRENCY("Currency is incorrect!"),
    LEGAL_CURRENCY_SIZE(3),
    FINALIZED("finalized");

    private String message;
    private int length;

    MessageCase(String message) {
        this.message = message;
    }

    MessageCase(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public String getMessage() {
        return message;
    }
}
