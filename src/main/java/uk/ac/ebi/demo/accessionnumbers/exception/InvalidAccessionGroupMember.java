package uk.ac.ebi.demo.accessionnumbers.exception;

import uk.ac.ebi.demo.accessionnumbers.AccessionNumber;

public class InvalidAccessionGroupMember extends RuntimeException {

    public InvalidAccessionGroupMember(String groupCode, AccessionNumber accessionNumber) {
        super(String.format("Accession Number [%s] does not belong to group [%s].",
                accessionNumber.toString(), groupCode));
    }

}
