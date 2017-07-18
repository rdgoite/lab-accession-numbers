package uk.ac.ebi.demo.accessionnumbers;

import org.junit.Test;

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

}
