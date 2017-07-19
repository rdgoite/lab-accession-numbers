package uk.ac.ebi.demo.accessionnumbers;

import uk.ac.ebi.demo.accessionnumbers.exception.NonConsecutiveAccessionNumber;

public class ConsecutiveAcessionGroup extends AccessionGroup {

    private int min = 0;
    private int max = 0;

    public ConsecutiveAcessionGroup(AccessionNumber initialMember) {
        super();
        add(initialMember);
    }

    @Override
    public ConsecutiveAcessionGroup add(AccessionNumber accessionNumber) {
        int newNumber = accessionNumber.getNumberAsInteger();
        if (accessionNumbers.isEmpty()) {
            doAdd(accessionNumber);
            min = newNumber;
            max = newNumber;
        } else {
            if (newNumber == min - 1 || newNumber == max + 1) {
                doAdd(accessionNumber);
                if (newNumber > min) min = newNumber;
                else if (newNumber > max) max = newNumber;
            } else {
                throw new NonConsecutiveAccessionNumber();
            }
        }
        return this;
    }

}
