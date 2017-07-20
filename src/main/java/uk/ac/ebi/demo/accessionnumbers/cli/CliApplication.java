package uk.ac.ebi.demo.accessionnumbers.cli;

import uk.ac.ebi.demo.accessionnumbers.AccessionNumber;
import uk.ac.ebi.demo.accessionnumbers.ConsecutiveAccessionGroup;
import uk.ac.ebi.demo.accessionnumbers.Sorter;

import java.util.List;
import java.util.stream.Collectors;

public class CliApplication {

    private void run() {
        String fileName = System.getProperty("input.file");
        if (fileName == null || fileName.isEmpty()) {
            System.out.println("This application expects 'input.file' property.");
            System.exit(1);
        }

        try {
            InputFileProcessor fileProcessor = new InputFileProcessor();
            List<String> inputs = fileProcessor.process(fileName);

            List<AccessionNumber> accessionNumbers = inputs.stream()
                    .map(AccessionNumber::parse)
                    .collect(Collectors.toList());

            Sorter sorter = new Sorter();
            //TODO check if list is empty
            List<String> results = sorter.sort(accessionNumbers).stream()
                    .map(ConsecutiveAccessionGroup::toString)
                    .collect(Collectors.toList());
            System.out.println(String.join(", ", results));
        } catch (Exception e) {
            String errorMessage = "An error occurred while processing the input: %s";
            errorMessage = String.format(errorMessage, e.getMessage());
            System.out.println(errorMessage);
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        new CliApplication().run();
    }

}
