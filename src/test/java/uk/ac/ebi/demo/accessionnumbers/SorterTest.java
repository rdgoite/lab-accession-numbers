package uk.ac.ebi.demo.accessionnumbers;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class SorterTest {

    @Test
    public void testSort() {
        //given:
        List<String> inputs = Arrays.asList("A0000", "ERR000111", "ERR000112", "ERR000113");
        List<AccessionNumber> accesionNumbers = inputs.stream()
                .map(input -> AccessionNumber.parse(input))
                .collect(Collectors.toList());

        //and:
        Sorter sorter = new Sorter();

        //when:
        List<ConsecutiveAccessionGroup> result = sorter.sort(accesionNumbers);

        //then:
        assertThat(result).hasSize(2);

        //and:
        ConsecutiveAccessionGroup firstEntry = result.get(0);
        assertThat(firstEntry.getMembers())
                .extracting("code", "number")
                .containsExactly(tuple("A", "0000"));

        //and:
        ConsecutiveAccessionGroup lastElement = result.get(1);
        assertThat(lastElement.getMembers())
                .extracting("code", "number")
                .containsExactly(tuple("ERR", "000111"), tuple("ERR", "000112"),
                        tuple("ERR", "000113"));
    }

}
