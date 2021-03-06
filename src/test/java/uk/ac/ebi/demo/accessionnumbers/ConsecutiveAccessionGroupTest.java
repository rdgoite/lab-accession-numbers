package uk.ac.ebi.demo.accessionnumbers;

import org.junit.Test;
import uk.ac.ebi.demo.accessionnumbers.exception.NonConsecutiveAccessionNumber;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsecutiveAccessionGroupTest {

    @Test
    public void testAddBeforeMin() {
        //given:
        String srrCode = "SRR";
        AccessionGroup group = createTestConsecutiveAccessionGroup(srrCode);

        //and:
        AccessionNumber srr005010 = new AccessionNumber(srrCode, "005010");

        //when:
        group.add(srr005010);

        //then:
        assertThat(group.getMembers()).contains(srr005010);
    }

    @Test
    public void testAddAfterMaximum() {
        //given:
        String errCode = "ERR";
        AccessionGroup group = createTestConsecutiveAccessionGroup(errCode);

        //and:
        AccessionNumber err005014 = new AccessionNumber(errCode, "005014");

        //when:
        group.add(err005014);

        //then:
        assertThat(group.getMembers()).contains(err005014);
    }

    @Test
    public void testAddNonConsecutive() {
        //given:
        String acCode = "AC";
        AccessionGroup group = createTestConsecutiveAccessionGroup(acCode);

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

    @Test
    public void testAccepts() {
        //given:
        String cmdCode = "CMD";
        ConsecutiveAccessionGroup group = createTestConsecutiveAccessionGroup(cmdCode);

        //and:
        AccessionNumber minAcceptable = new AccessionNumber(cmdCode, "005010");
        AccessionNumber minUnacceptable = new AccessionNumber(cmdCode, "005009");
        AccessionNumber maxAcceptable = new AccessionNumber(cmdCode, "005014");
        AccessionNumber maxUnacceptable = new AccessionNumber(cmdCode, "005019");

        //and:
        AccessionNumber invalidCode = new AccessionNumber("CMG", "005010");
        AccessionNumber nonMatchingNumber = new AccessionNumber(cmdCode, "05014");

        //expect:
        assertThat(group.accepts(minAcceptable)).isTrue();
        assertThat(group.accepts(minUnacceptable)).isFalse();
        assertThat(group.accepts(maxAcceptable)).isTrue();
        assertThat(group.accepts(maxUnacceptable)).isFalse();

        //and:
        assertThat(group.accepts(invalidCode)).isFalse();
        assertThat(group.accepts(nonMatchingNumber)).isFalse();
    }

    @Test
    public void testToString() {
        //given:
        ConsecutiveAccessionGroup emptyGroup = new ConsecutiveAccessionGroup();
        ConsecutiveAccessionGroup singleMemberGroup = new ConsecutiveAccessionGroup(new
                AccessionNumber("SRR", "2900101"));

        //and:
        ConsecutiveAccessionGroup multipleMemberGroup = createTestConsecutiveAccessionGroup("ABC");

        //expect:
        assertThat(emptyGroup.toString()).isEqualTo("[]");
        assertThat(singleMemberGroup.toString()).isEqualTo("SRR2900101");
        assertThat(multipleMemberGroup.toString()).isEqualTo("ABC005011-ABC005013");
    }

    private ConsecutiveAccessionGroup createTestConsecutiveAccessionGroup(String code) {
        AccessionNumber ac005012 = new AccessionNumber(code, "005012");
        ConsecutiveAccessionGroup group = new ConsecutiveAccessionGroup(ac005012);

        AccessionNumber ac005011 = new AccessionNumber(code, "005011");
        AccessionNumber ac00513 = new AccessionNumber(code, "005013");
        group.add(ac005011).add(ac00513);
        return group;
    }

}
