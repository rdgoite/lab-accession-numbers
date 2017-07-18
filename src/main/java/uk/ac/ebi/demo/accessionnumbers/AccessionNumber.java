package uk.ac.ebi.demo.accessionnumbers;

import uk.ac.ebi.demo.accessionnumbers.exception.InvalidAcessionNumberPattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccessionNumber {

    private final String code;
    private final String number;

    //TODO add processing for character casing
    public static AccessionNumber parse(String input) {
        Pattern pattern = Pattern.compile("^(?<code>\\p{Alpha}+)(?<number>\\p{Digit}+)$");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            return new AccessionNumber(matcher.group("code"), matcher.group("number"));
        } else {
            throw new InvalidAcessionNumberPattern();
        }
    }

    public AccessionNumber(String code, String number) {
        this.code = code;
        this.number = number;
    }

    public String getGroupCode() {
        return String.format("%s%d", code, number.length());
    }

}
