package models.enums;

public enum Status {
    /**
     * @param score - values: 'pass', 'fail', or 'unset'
     */

    PASS("pass"),
    FAIL("fail"),
    UNSET("unset");

    public final String label;

    Status(String label) {
        this.label = label;
    }
}
