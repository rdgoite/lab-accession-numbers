package uk.ac.ebi.demo.accessionnumbers;

import uk.ac.ebi.demo.accessionnumbers.exception.InvalidAcessionNumberPattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccessionNumber {

    private final String code;
    private final String number;

    private final String groupCode;

    public static AccessionNumber parse(String input) {
        Pattern pattern = Pattern.compile("^(?<code>\\p{Alpha}+)(?<number>\\p{Digit}+)$");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            return new AccessionNumber(matcher.group("code"), matcher.group("number"));
        } else {
            throw new InvalidAcessionNumberPattern();
        }
    }

    //TODO add validation for code and number arguments?
    public AccessionNumber(String code, String number) {
        this.code = code.toUpperCase();
        this.number = number;
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

}
