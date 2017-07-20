package uk.ac.ebi.demo.accessionnumbers;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class SorterTest {

    private Sorter sorter = new Sorter();

    @Test
    public void testSort() {
        //given:
        List<AccessionNumber> accesionNumbers = process("A0000", "ERR000111", "ERR000112",
                "ERR000113");

        //when:
        List<ConsecutiveAccessionGroup> result = sorter.sort(accesionNumbers);

        //then:
        assertThat(result).hasSize(2);

        //and:
        ConsecutiveAccessionGroup firstElement = result.get(0);
        assertThat(firstElement.getMembers())
                .extracting("code", "number")
                .containsExactly(tuple("A", "0000"));

        //and:
        ConsecutiveAccessionGroup secondElement = result.get(1);
        assertThat(secondElement.getMembers())
                .extracting("code", "number")
                .containsExactly(tuple("ERR", "000111"), tuple("ERR", "000112"),
                        tuple("ERR", "000113"));
    }

    @Test
    public void testSortCorrectOrderForSimilarInputs() {
        //given:
        List<AccessionNumber> accessionNumbers = process("SRR002007", "SRR002005", "SRR002011",
                "SRR002001", "SRR002006", "SRR002002");

        //when:
        List<ConsecutiveAccessionGroup> result = sorter.sort(accessionNumbers);

        //then:
        assertThat(result).hasSize(3);

        //and:
        ConsecutiveAccessionGroup firstElement = result.get(0);
        assertThat(firstElement.getMembers())
                .extracting("code", "number")
                .containsExactly(tuple("SRR", "002001"), tuple("SRR", "002002"));

        //and:
        ConsecutiveAccessionGroup secondElement = result.get(1);
        assertThat(secondElement.getMembers())
                .extracting("code", "number")
                .containsExactly(tuple("SRR", "002005"), tuple("SRR", "002006"),
                        tuple("SRR", "002007"));

        //and:
        ConsecutiveAccessionGroup thirdElement = result.get(2);
        assertThat(thirdElement.getMembers())
                .extracting("code", "number")
                .containsExactly(tuple("SRR", "002011"));
    }

    @Test
    public void testSortCorrectOrderForSimilarGroups() {
        //given:
        List<AccessionNumber> accessionNumbers = process("A00000", "A0001");

        //when:
        List<ConsecutiveAccessionGroup> result = sorter.sort(accessionNumbers);

        //then:
        assertThat(result).hasSize(2);

        //and:
        ConsecutiveAccessionGroup firstElement = result.get(0);
        assertThat(firstElement.getMembers())
                .extracting("code", "number")
                .containsExactly(tuple("A", "00000"));

        //and:
        ConsecutiveAccessionGroup secondElement = result.get(1);
        assertThat(secondElement.getMembers())
                .extracting("code", "number")
                .containsExactly(tuple("A", "0001"));
    }

    @Test
    public void testSortCorrectOrderBasedOnLength() {
        //given:
        List<AccessionNumber> accessionNumbers = process("DDR0001", "DDR01");

        //when:
        List<ConsecutiveAccessionGroup> result = sorter.sort(accessionNumbers);

        //then:
        assertThat(result).hasSize(2);

        //and:
        ConsecutiveAccessionGroup firstElement = result.get(0);
        assertThat(firstElement.getMembers())
                .extracting("code", "number")
                .containsExactly(tuple("DDR", "01"));

        //and:
        ConsecutiveAccessionGroup secondElement = result.get(1);
        assertThat(secondElement.getMembers())
                .extracting("code", "number")
                .containsExactly(tuple("DDR", "0001"));
    }

    private List<AccessionNumber> process(String... inputs) {
        return Arrays.stream(inputs)
                .map(input -> AccessionNumber.parse(input))
                .collect(Collectors.toList());
    }

}
