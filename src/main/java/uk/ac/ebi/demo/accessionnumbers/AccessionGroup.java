package uk.ac.ebi.demo.accessionnumbers;

import uk.ac.ebi.demo.accessionnumbers.exception.InvalidAccessionGroupMember;

import java.util.*;

//TODO consider duplicate members (uniqueness requirement)
// - no need to check because of shift to map internal structure
// - however, exceptions can be raised if checks are necessary?
public class AccessionGroup {

    protected final SortedMap<Integer, AccessionNumber> accessionNumbers;

    protected String code = "";
    protected String accessionCode = "";

    public AccessionGroup() {
        accessionNumbers = new TreeMap<>();
    }

    public AccessionGroup(AccessionNumber initialMember) {
        this();
        doAdd(initialMember);
    }

    public String getCode() {
        return code;
    }

    public String getAccessionCode() {
        return accessionCode;
    }

    public AccessionGroup add(AccessionNumber accessionNumber) {
        doAdd(accessionNumber);
        return this;
    }

    protected void doAdd(AccessionNumber accessionNumber) {
        String candidateGroupCode = accessionNumber.getGroupCode();
        if (accessionNumbers.isEmpty() || code.equals(candidateGroupCode)) {
            if (code.isEmpty()) {
                code = candidateGroupCode;
                accessionCode = accessionNumber.getCode();
            }
            accessionNumbers.put(accessionNumber.getNumberAsInteger(), accessionNumber);
        } else {
            throw new InvalidAccessionGroupMember(code, accessionNumber);
        }
    }

    public int size() {
        return accessionNumbers.size();
    }

    public List<ConsecutiveAccessionGroup> collapseConsecutive() {
        List<ConsecutiveAccessionGroup> consecutiveGroups = new ArrayList<>();
        ConsecutiveAccessionGroup currentGroup = new ConsecutiveAccessionGroup();
        for (Map.Entry<Integer, AccessionNumber> entry : accessionNumbers.entrySet()) {
            AccessionNumber value = entry.getValue();
            if (currentGroup.accepts(value)) {
                currentGroup.add(value);
            } else {
                currentGroup = new ConsecutiveAccessionGroup(value);
                consecutiveGroups.add(currentGroup);
            }
        }
        return consecutiveGroups;
    }

    public Collection<AccessionNumber> getMembers() {
        return Collections.unmodifiableCollection(accessionNumbers.values());
    }

}
