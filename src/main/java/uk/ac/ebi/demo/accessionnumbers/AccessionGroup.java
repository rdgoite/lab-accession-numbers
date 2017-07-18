package uk.ac.ebi.demo.accessionnumbers;

import uk.ac.ebi.demo.accessionnumbers.exception.InvalidAccessionGroupMember;

import java.util.ArrayList;
import java.util.List;

public class AccessionGroup {

    private final List<AccessionNumber> accessionNumbers;

    private String code;

    public AccessionGroup() {
        accessionNumbers = new ArrayList<>();
    }

    public AccessionGroup(AccessionNumber startingMember) {
        this();
        add(startingMember);
    }

    public void add(AccessionNumber accessionNumber) {
        String candidateGroupCode = accessionNumber.getGroupCode();
        if (accessionNumbers.isEmpty() || code.equals(candidateGroupCode)) {
            code = candidateGroupCode;
            accessionNumbers.add(accessionNumber);
        } else {
            throw new InvalidAccessionGroupMember(code, accessionNumber);
        }
    }

    public String getCode() {
        return code;
    }

    public int size() {
        return accessionNumbers.size();
    }

}
