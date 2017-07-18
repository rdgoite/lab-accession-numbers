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

}
