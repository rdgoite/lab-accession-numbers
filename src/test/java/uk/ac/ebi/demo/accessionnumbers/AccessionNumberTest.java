package uk.ac.ebi.demo.accessionnumbers;

import org.junit.Test;
import uk.ac.ebi.demo.accessionnumbers.exception.InvalidAcessionNumberPattern;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class AccessionNumberTest {

    @Test
    public void testParse() {
        //given:
        String validInput1 = "ERR000111";
        String validInput2 = "A00001";

        //when:
        AccessionNumber accessionNumber1 = AccessionNumber.parse(validInput1);
        AccessionNumber accessionNumber2 = AccessionNumber.parse(validInput2);

        //then:
        assertThat(accessionNumber1)
                .extracting("code", "number")
                .containsExactly("ERR", "000111");

        //and:
        assertThat(accessionNumber2)
                .extracting("code", "number")
                .containsExactly("A", "00001");
    }

    @Test
    public void testParseInvalidInput() {
        //expect:
        asList("", "011EAB", "_CCD0001", "CC000159B").forEach(invalidInput -> {
            doTestParseInvalidInput(invalidInput);
        });
    }

    private void doTestParseInvalidInput(String invalidInput) {
        //when:
        boolean exceptionThrown = false;
        try {
            AccessionNumber.parse(invalidInput);
        } catch (InvalidAcessionNumberPattern exception) {
            exceptionThrown = true;
        }

        //then:
        assertThat(exceptionThrown).isTrue();
    }

}
