package uk.ac.ebi.demo.accessionnumbers;

import java.util.*;

public class Sorter {

    public List<ConsecutiveAccessionGroup> sort(List<AccessionNumber> accesionNumbers) {
        final SortedMap<String, AccessionGroup> groupMap = new TreeMap<>();
        accesionNumbers.forEach(accessionNumber -> {
            String groupCode = accessionNumber.getGroupCode();
            if (groupMap.containsKey(groupCode)) {
                 groupMap.get(groupCode).add(accessionNumber);
            } else {
                groupMap.put(groupCode, new AccessionGroup(accessionNumber));
            }
        });
        final List<ConsecutiveAccessionGroup> result = new ArrayList<>();
        groupMap.values().forEach(group -> {
            result.addAll(group.collapseConsecutive());
        });
        return result;
    }

}
