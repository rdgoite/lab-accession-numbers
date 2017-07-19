package uk.ac.ebi.demo.accessionnumbers;

import uk.ac.ebi.demo.accessionnumbers.exception.InvalidAccessionGroupMember;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

//TODO consider duplicate members (uniqueness requirement)
public class AccessionGroup {

    protected final List<AccessionNumber> accessionNumbers;

    protected String code;

    public AccessionGroup() {
        accessionNumbers = new ArrayList<>();
    }

    public AccessionGroup(AccessionNumber initialMember) {
        this();
        doAdd(initialMember);
    }

    public AccessionGroup add(AccessionNumber accessionNumber) {
        doAdd(accessionNumber);
        return this;
    }

    protected void doAdd(AccessionNumber accessionNumber) {
        String candidateGroupCode = accessionNumber.getGroupCode();
        if (accessionNumbers.isEmpty() || code.equals(candidateGroupCode)) {
            if (code == null) code = candidateGroupCode;
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

    public List<AccessionGroup> collapseConsecutive() {
        SortedMap<Integer, AccessionNumber> map = new TreeMap<>(accessionNumbers.stream()
                .collect(Collectors.toMap(AccessionNumber::getNumberAsInteger,
                        Function.identity())));
        List<AccessionGroup> consecutiveGroups = new ArrayList<>();
        int lastNumber = 0;
        AccessionGroup currentGroup = null;
        for (Map.Entry<Integer, AccessionNumber> entry : map.entrySet()) {
            AccessionNumber value = entry.getValue();
            if (entry.getKey() == lastNumber + 1) {
                currentGroup.add(value);
            } else {
                currentGroup = new AccessionGroup(value);
                consecutiveGroups.add(currentGroup);
            }
            lastNumber = value.getNumberAsInteger();
        }
        return consecutiveGroups;
    }

    public Collection<AccessionNumber> getMembers() {
        return Collections.unmodifiableCollection(accessionNumbers);
    }

}
