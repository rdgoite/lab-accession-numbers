package uk.ac.ebi.demo.accessionnumbers;

import org.junit.Test;
import uk.ac.ebi.demo.accessionnumbers.exception.InvalidAcessionNumberPattern;

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
        //given:
        String invalidPattern = "011EAB";

        //when:
        boolean exceptionThrown = false;
        try {
            AccessionNumber.parse(invalidPattern);
        } catch (InvalidAcessionNumberPattern exception) {
            exceptionThrown = true;
        }

        //then:
        assertThat(exceptionThrown).isTrue();
    }

}
