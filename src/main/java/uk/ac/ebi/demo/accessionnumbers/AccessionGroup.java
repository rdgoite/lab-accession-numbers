package uk.ac.ebi.demo.accessionnumbers;

import java.util.ArrayList;
import java.util.List;

public class AccessionGroup {

    private final List<AccessionNumber> accessionNumbers;

    private String code;

    public AccessionGroup() {
        accessionNumbers = new ArrayList<>();
    }

    public void add(AccessionNumber accessionNumber) {
        code = accessionNumber.getGroupCode();
    }

    public String getCode() {
        return code;
    }

}
