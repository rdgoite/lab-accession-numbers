package uk.ac.ebi.demo.accessionnumbers.cli.exception;

public class NonExistentFile extends RuntimeException {

    public NonExistentFile(String fileName) {
        super(String.format("File [%s] does not exist.", fileName));
    }

}
