package uk.ac.ebi.demo.accessionnumbers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccessionNumber {

    private final String code;
    private final String number;

    //TODO add handling for invalid input
    //TODO add processing for character casing
    public static AccessionNumber parse(String input) {
        Pattern pattern = Pattern.compile("^(?<code>\\p{Alpha}+)(?<number>\\p{Digit}+)$");
        Matcher matcher = pattern.matcher(input);
        matcher.matches();
        return new AccessionNumber(matcher.group("code"), matcher.group("number"));
    }

    public AccessionNumber(String code, String number) {
        this.code = code;
        this.number = number;
    }

}
