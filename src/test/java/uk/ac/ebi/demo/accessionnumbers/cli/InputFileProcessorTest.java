package uk.ac.ebi.demo.accessionnumbers.cli;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import uk.ac.ebi.demo.accessionnumbers.cli.exception.NonExistentFile;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class InputFileProcessorTest {

    @Rule
    public TemporaryFolder directory = new TemporaryFolder();

    @Test
    public void testProcess() throws Exception {
        //given:
        File inputFile = directory.newFile("accession-numbers.dat");

        //and:
        PrintWriter printWriter = new PrintWriter(inputFile);
        List<String> dataLine1 = asList("A0001", "ERR01011", "DMEF00001920");
        printWriter.print(String.join(" , ", dataLine1));
        printWriter.println(" , ");
        List<String> dataLine2 = asList("EFF10001", "EFF10002", "EFF10003");
        printWriter.println(String.join(",", dataLine2));
        printWriter.flush();

        //and:
        InputFileProcessor inputFileProcessor = new InputFileProcessor();

        //when:
        List<String> inputs = inputFileProcessor.process(inputFile.getAbsolutePath());

        //then:
        ArrayList<String> expectedInputs = new ArrayList<>(dataLine1);
        expectedInputs.addAll(dataLine2);
        assertThat(inputs).containsExactlyElementsOf(expectedInputs);
    }

    @Test
    public void testProcessNonExistentFile() {
        //given:
        InputFileProcessor inputFileProcessor = new InputFileProcessor();

        //when:
        boolean exceptionThrown = false;
        try {
            inputFileProcessor.process("non-existent.dat");
        } catch (NonExistentFile exception) {
            exceptionThrown = true;
        }

        //then:
        assertThat(exceptionThrown).isTrue();
    }

}
