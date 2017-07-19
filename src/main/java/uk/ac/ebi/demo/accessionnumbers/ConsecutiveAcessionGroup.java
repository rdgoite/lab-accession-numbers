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
            if (isAcceptable(newNumber)) {
                doAdd(accessionNumber);
                if (newNumber < min) min = newNumber;
                else if (newNumber > max) max = newNumber;
            } else {
                throw new NonConsecutiveAccessionNumber();
            }
        }
        return this;
    }

    public boolean accepts(AccessionNumber accessionNumber) {
        int number = accessionNumber.getNumberAsInteger();
        return isAcceptable(number);
    }

    private boolean isAcceptable(int number) {
        return number == min - 1 || number == max + 1;
    }

}
