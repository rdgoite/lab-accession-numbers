package uk.ac.ebi.demo.accessionnumbers.exception;

public class InvalidAcessionNumberPattern extends RuntimeException {

    public InvalidAcessionNumberPattern() {
        super("The String is not valid Accession Number.");
    }

}
