package uk.ac.ebi.demo.accessionnumbers;

import uk.ac.ebi.demo.accessionnumbers.exception.InvalidAccessionGroupMember;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

//TODO consider duplicate members (uniqueness requirement)
public class AccessionGroup {

    protected final SortedMap<Integer, AccessionNumber> accessionNumbers;

    protected String code;

    public AccessionGroup() {
        accessionNumbers = new TreeMap<>();
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
            accessionNumbers.put(accessionNumber.getNumberAsInteger(), accessionNumber);
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

    public List<ConsecutiveAcessionGroup> collapseConsecutive() {
        List<ConsecutiveAcessionGroup> consecutiveGroups = new ArrayList<>();
        ConsecutiveAcessionGroup currentGroup = new ConsecutiveAcessionGroup();
        for (Map.Entry<Integer, AccessionNumber> entry : accessionNumbers.entrySet()) {
            AccessionNumber value = entry.getValue();
            if (currentGroup.accepts(value)) {
                currentGroup.add(value);
            } else {
                currentGroup = new ConsecutiveAcessionGroup(value);
                consecutiveGroups.add(currentGroup);
            }
        }
        return consecutiveGroups;
    }

    public Collection<AccessionNumber> getMembers() {
        return Collections.unmodifiableCollection(accessionNumbers.values());
    }

}
