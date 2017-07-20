package uk.ac.ebi.demo.accessionnumbers.cli;

import uk.ac.ebi.demo.accessionnumbers.cli.exception.NonExistentFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class InputFileProcessor {

    public List<String> process(String inputPath) {
        final List<String> inputs = new ArrayList<>();
        try {
            Path path = Paths.get(inputPath);
            if (Files.exists(path)) {
                Stream<String> stream = Files.lines(path);
                stream.forEach(inputLine -> {
                    String[] data = inputLine.trim().split(",");
                    Arrays.stream(data).forEach(datum -> {
                        inputs.add(datum.trim());
                    });
                });
                return inputs;
            } else {
                throw new NonExistentFile(inputPath);
            }
        } catch (IOException cause) {
            throw new RuntimeException(cause);
        }
    }

}
