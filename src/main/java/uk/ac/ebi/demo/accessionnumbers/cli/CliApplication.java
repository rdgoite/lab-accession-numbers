package uk.ac.ebi.demo.accessionnumbers.cli;

import uk.ac.ebi.demo.accessionnumbers.AccessionNumber;
import uk.ac.ebi.demo.accessionnumbers.ConsecutiveAccessionGroup;
import uk.ac.ebi.demo.accessionnumbers.Sorter;

import java.util.List;
import java.util.stream.Collectors;

public class CliApplication {

    private void run() {
        String fileName = System.getProperty("input.file");
        checkIfProvided(fileName);
        try {
            List<AccessionNumber> accessionNumbers = processInput(fileName);
            if (!accessionNumbers.isEmpty()) {
                Sorter sorter = new Sorter();
                List<String> results = sorter.sort(accessionNumbers).stream()
                        .map(ConsecutiveAccessionGroup::toString)
                        .collect(Collectors.toList());
                System.out.println(String.join(", ", results));
            } else {
                System.out.println("No accession numbers provided.");
            }
        } catch (Exception e) {
            String errorMessage = "An error occurred while processing the input: %s";
            errorMessage = String.format(errorMessage, e.getMessage());
            System.out.println(errorMessage);
            System.exit(1);
        }
    }

    private void checkIfProvided(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            System.out.println("This application expects 'input.file' property.");
            System.exit(1);
        }
    }

    private List<AccessionNumber> processInput(String fileName) {
        InputFileProcessor fileProcessor = new InputFileProcessor();
        List<String> inputs = fileProcessor.process(fileName);
        return inputs.stream()
                .map(AccessionNumber::parse)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        new CliApplication().run();
    }

}
