package uk.ac.ebi.demo.accessionnumbers;

import uk.ac.ebi.demo.accessionnumbers.exception.InvalidAccessionGroupMember;

import java.util.ArrayList;
import java.util.Arrays;
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

    public AccessionGroup add(AccessionNumber accessionNumber) {
        String candidateGroupCode = accessionNumber.getGroupCode();
        if (accessionNumbers.isEmpty() || code.equals(candidateGroupCode)) {
            if (code == null) code = candidateGroupCode;
            accessionNumbers.add(accessionNumber);
            return this;
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

    public List<AccessionGroup> collapseConsecutive() {
        return Arrays.asList(new AccessionGroup());
    }

}
