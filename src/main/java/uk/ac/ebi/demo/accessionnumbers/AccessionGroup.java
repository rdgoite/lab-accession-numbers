package uk.ac.ebi.demo.accessionnumbers;

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
        code = accessionNumber.getGroupCode();
        accessionNumbers.add(accessionNumber);
    }

    public String getCode() {
        return code;
    }

    public int size() {
        return accessionNumbers.size();
    }

}
