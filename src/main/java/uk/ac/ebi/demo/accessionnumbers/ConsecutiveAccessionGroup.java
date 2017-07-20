package uk.ac.ebi.demo.accessionnumbers;

import uk.ac.ebi.demo.accessionnumbers.exception.NonConsecutiveAccessionNumber;

//TODO composition over inheritance?
public class ConsecutiveAccessionGroup extends AccessionGroup {

    private int min = 0;
    private int max = 0;

    public ConsecutiveAccessionGroup() {
        super();
    }

    public ConsecutiveAccessionGroup(AccessionNumber initialMember) {
        this();
        add(initialMember);
    }

    @Override
    public ConsecutiveAccessionGroup add(AccessionNumber accessionNumber) {
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

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }

    public boolean accepts(AccessionNumber accessionNumber) {
        int number = accessionNumber.getNumberAsInteger();
        return code.equals(accessionNumber.getGroupCode()) && isAcceptable(number);
    }

    private boolean isAcceptable(int number) {
        return number == min - 1 || number == max + 1;
    }

}
