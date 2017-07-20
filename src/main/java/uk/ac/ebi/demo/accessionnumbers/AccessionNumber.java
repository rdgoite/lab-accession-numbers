package uk.ac.ebi.demo.accessionnumbers;

import uk.ac.ebi.demo.accessionnumbers.exception.InvalidAcessionNumberPattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccessionNumber {

    public static final Pattern CODE_PATTERN = Pattern.compile("^\\s*\\p{Alpha}+\\s*$");
    public static final Pattern NUMBER_PATTERN = Pattern.compile("^\\s*\\p{Digit}+\\s*$");

    public static final Pattern PARSE_PATTERN = Pattern.compile("^\\s*(?<code>\\p{Alpha}+)" +
            "(?<number>\\p{Digit}+)\\s*$");

    private final String code;
    private final String number;

    private final String groupCode;


    public static AccessionNumber parse(String input) {
        Matcher matcher = PARSE_PATTERN.matcher(input);
        if (matcher.matches()) {
            return new AccessionNumber(matcher.group("code"), matcher.group("number"));
        } else {
            throw new InvalidAcessionNumberPattern();
        }
    }

    public AccessionNumber(String code, String number) {
        if (!CODE_PATTERN.matcher(code).matches() || !NUMBER_PATTERN.matcher(number).matches()) {
            throw new InvalidAcessionNumberPattern();
        }
        this.code = code.trim().toUpperCase();
        this.number = number.trim();
        groupCode = String.format("%s%d", code, number.length());
    }

    public String getCode() {
        return code;
    }

    public String getNumber() {
        return number;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public int getNumberAsInteger() {
        return Integer.parseInt(number);
    }

    @Override
    public String toString() {
        return String.format("%s%s", code, number);
    }

}
