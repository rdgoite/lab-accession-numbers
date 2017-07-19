package uk.ac.ebi.demo.accessionnumbers;

import org.junit.Test;
import uk.ac.ebi.demo.accessionnumbers.exception.NonConsecutiveAccessionNumber;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsecutiveAcessionGroupTest {

    @Test
    public void testAddNonConsecutive() {
        //given:
        String acCode = "AC";
        AccessionNumber ac005012 = new AccessionNumber(acCode, "005012");
        AccessionGroup group = new ConsecutiveAcessionGroup(ac005012);

        //and:
        AccessionNumber ac005011 = new AccessionNumber(acCode, "005011");
        AccessionNumber ac00513 = new AccessionNumber(acCode, "005013");
        group.add(ac005011).add(ac00513);

        //and:
        AccessionNumber ac005017 = new AccessionNumber(acCode, "005017");

        //when:
        boolean exceptionThrown = false;
        try {
            group.add(ac005017);
        } catch (NonConsecutiveAccessionNumber exception) {
            exceptionThrown = true;
        }

        //then:
        assertThat(exceptionThrown).isTrue();
    }

}
