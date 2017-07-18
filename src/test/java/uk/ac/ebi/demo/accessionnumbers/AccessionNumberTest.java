package uk.ac.ebi.demo.accessionnumbers;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AccessionNumberTest {

    @Test
    public void testParse() {
        //given:
        String validInput = "ERR000111";

        //when:
        AccessionNumber accessionNumber = AccessionNumber.parse(validInput);

        //then:
        assertThat(accessionNumber)
                .isNotNull()
                .extracting("code", "number")
                .containsExactly("ERR", "000111");
    }

}
