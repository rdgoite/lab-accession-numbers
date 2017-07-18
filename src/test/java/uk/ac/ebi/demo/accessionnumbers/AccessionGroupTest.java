package uk.ac.ebi.demo.accessionnumbers;

import org.junit.Test;
import uk.ac.ebi.demo.accessionnumbers.exception.InvalidAccessionGroupMember;

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

}
