package uk.ac.ebi.demo.accessionnumbers;

import org.junit.Test;
import uk.ac.ebi.demo.accessionnumbers.exception.InvalidAccessionGroupMember;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class AccessionGroupTest {

    @Test
    public void testAddOnEmpty() {
        //given:
        AccessionGroup group = new AccessionGroup();
        AccessionNumber srr211001 = new AccessionNumber("SRR", "211001");

        //when:
        group.add(srr211001);

        //then:
        assertThat(group.getCode()).isEqualTo(srr211001.getGroupCode());
    }

    @Test
    public void testAddValidEntries() {
        //given:
        AccessionNumber abc110012 = new AccessionNumber("ABC", "110012");
        AccessionGroup group = new AccessionGroup(abc110012);
        assertThat(group.getCode()).isEqualTo(abc110012.getGroupCode());

        //and
        AccessionNumber abc110117 = new AccessionNumber("ABC", "110117");
        AccessionNumber abc210211 = new AccessionNumber("ABC", "210211");

        //when:
        group.add(abc110117);
        group.add(abc210211);

        //then:
        assertThat(group.size()).isEqualTo(3);
    }

    @Test
    public void testAddInvalidEntry() {
        //given:
        AccessionNumber xyz2010 = new AccessionNumber("XYZ", "2010");
        AccessionGroup group = new AccessionGroup(xyz2010);
        assertThat(group.getCode()).isEqualTo(xyz2010.getGroupCode());

        //and:
        AccessionNumber xyz20122 = new AccessionNumber("XYZ", "20122");

        //when:
        boolean exceptionThrown = false;
        try {
            group.add(xyz20122);
        } catch (InvalidAccessionGroupMember e) {
            exceptionThrown = true;
        }

        //then:
        assertThat(exceptionThrown).isTrue();
    }

    @Test
    public void testCollapseConsecutive() {
        //given:
        String acCode = "AC";
        AccessionNumber ac0020010 = new AccessionNumber(acCode, "0020010");
        AccessionNumber ac0020011 = new AccessionNumber(acCode, "0020011");
        AccessionNumber ac0020012 = new AccessionNumber(acCode, "0020012");

        //and:
        AccessionGroup group = new AccessionGroup(ac0020010);
        group.add(ac0020011).add(ac0020012);

        //when:
        List<ConsecutiveAcessionGroup> consecutiveGroups = group.collapseConsecutive();

        //then:
        assertThat(consecutiveGroups).hasSize(1);

        //and:
        AccessionGroup mainGroup = consecutiveGroups.get(0);
        assertThat(mainGroup.getCode()).isEqualTo("AC7");
        assertThat(mainGroup.getMembers()).hasSize(3);

        //and:
        List<Integer> mainGroupNumbers = mainGroup.getMembers().stream()
                .map(AccessionNumber::getNumberAsInteger)
                .collect(Collectors.toList());
        assertThat(mainGroupNumbers).containsExactlyInAnyOrder(20010, 20011, 20012);
    }

    @Test
    public void testCollapseConsecutiveMultipleGroups() {
        //given:
        String mvcCode = "MVC";
        AccessionNumber mvc001101 = new AccessionNumber(mvcCode, "001101");
        AccessionGroup testGroup = new AccessionGroup(mvc001101);

        //and:
        AccessionNumber mvc002101 = new AccessionNumber(mvcCode, "002101");
        AccessionNumber mvc002100 = new AccessionNumber(mvcCode, "002100");
        testGroup.add(mvc002101).add(mvc002100);

        //when:
        List<ConsecutiveAcessionGroup> consecutiveGroups = testGroup.collapseConsecutive();

        //then:
        assertThat(consecutiveGroups).hasSize(2);

        //and:
        AccessionGroup singleMemberGroup = consecutiveGroups.stream()
                .filter(group -> group.size() == 1)
                .findFirst().get();
        List<String> singleMemberGroupNumbers = singleMemberGroup.getMembers().stream()
                .map(AccessionNumber::getNumber)
                .collect(Collectors.toList());
        assertThat(singleMemberGroupNumbers).containsExactly("001101");

        //and:
        AccessionGroup twoMemberGroup = consecutiveGroups.stream()
                .filter(group -> group.size() == 2)
                .findFirst().get();
        List<String> twoMemberGroupNumbers = twoMemberGroup.getMembers().stream()
                .map(AccessionNumber::getNumber)
                .collect(Collectors.toList());
        assertThat(twoMemberGroupNumbers).containsExactly("002100", "002101");
    }

}
