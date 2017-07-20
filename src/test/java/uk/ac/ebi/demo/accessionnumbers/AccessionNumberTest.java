package uk.ac.ebi.demo.accessionnumbers;

import org.junit.Test;
import uk.ac.ebi.demo.accessionnumbers.exception.InvalidAcessionNumberPattern;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class AccessionNumberTest {

    @Test
    public void testImposeUniformCasing() {
        //given:
        AccessionNumber allUpperCase = new AccessionNumber("ABC", "123");
        AccessionNumber allLowerCase = new AccessionNumber("xyz", "90012");
        AccessionNumber mixedCase = new AccessionNumber("sRrEDw", "2901");

        //expect:
        assertThat(allUpperCase.getCode()).isEqualTo("ABC");
        assertThat(allLowerCase.getCode()).isEqualTo("XYZ");
        assertThat(mixedCase.getCode()).isEqualTo("SRREDW");
    }

    @Test
    public void testTrimCode() {
        //given:
        AccessionNumber accessionNumber = new AccessionNumber(" SRR ", "8102");

        //expect:
        assertThat(accessionNumber.getCode()).isEqualTo("SRR");
    }

    @Test
    public void testValidateConstructorArguments() {
        //expect:
        doTestValidateConstructorArguments("!_INVALID_CODE_!", "78900");
        doTestValidateConstructorArguments("ABC", "not a valid number");
    }

    private void doTestValidateConstructorArguments(String code, String number) {
        //when:
        boolean exceptionThrown = false;
        try {
            new AccessionNumber(code, number);
        } catch (InvalidAcessionNumberPattern exception) {
            exceptionThrown = true;
        }

        //then:
        assertThat(exceptionThrown).as("expecting to throw exception").isTrue();
    }

    @Test
    public void testParse() {
        //given:
        String validInput1 = "ERR000111";
        String validInput2 = "A00001";
        String validInputWithSpace = "   EFR1001001 ";

        //when:
        AccessionNumber accessionNumber1 = AccessionNumber.parse(validInput1);
        AccessionNumber accessionNumber2 = AccessionNumber.parse(validInput2);
        AccessionNumber accessionNumber3 = AccessionNumber.parse(validInputWithSpace);

        //then:
        assertThat(accessionNumber1)
                .extracting("code", "number")
                .containsExactly("ERR", "000111");

        //and:
        assertThat(accessionNumber2)
                .extracting("code", "number")
                .containsExactly("A", "00001");

        //and:
        assertThat(accessionNumber3)
                .extracting("code", "number")
                .containsExactly("EFR", "1001001");
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

    @Test
    public void testGetGroupCode() {
        //given:
        AccessionNumber err200010 = new AccessionNumber("ERR", "200010");
        AccessionNumber a00001 = new AccessionNumber("A", "00001");
        AccessionNumber drm01 = new AccessionNumber("DRM", "01");

        //expect:
        assertThat(err200010.getGroupCode()).isEqualTo("ERR6");
        assertThat(a00001.getGroupCode()).isEqualTo("A5");
        assertThat(drm01.getGroupCode()).isEqualTo("DRM2");
    }

    @Test
    public void testGetNumberAsInteger() {
        //given:
        AccessionNumber ddr20100 = new AccessionNumber("DDR", "20100");
        AccessionNumber ab000901 = new AccessionNumber("AB", "000901");
        AccessionNumber xyz0000 = new AccessionNumber("XYZ", "0000");

        //expect:
        assertThat(ddr20100.getNumberAsInteger()).isEqualTo(20100);
        assertThat(ab000901.getNumberAsInteger()).isEqualTo(901);
        assertThat(xyz0000.getNumberAsInteger()).isEqualTo(0);
    }

}
