package uk.ac.ebi.demo.accessionnumbers;

import java.util.*;

public class Sorter {

    public List<ConsecutiveAccessionGroup> sort(List<AccessionNumber> accesionNumbers) {
        final SortedMap<String, AccessionGroup> groupMap = group(accesionNumbers);
        final List<ConsecutiveAccessionGroup> result = new ArrayList<>();
        groupMap.values().forEach(group -> {
            result.addAll(group.collapseConsecutive());
        });
        result.sort((group1, goup2) -> {
            int accessionCodeComparison = group1.getAccessionCode().compareTo(
                    goup2.getAccessionCode());
            if (accessionCodeComparison == 0) {
                return group1.getMin().compareTo(goup2.getMin());
            } else {
                return accessionCodeComparison;
            }
        });
        return result;
    }

    private SortedMap<String, AccessionGroup> group(List<AccessionNumber> accesionNumbers) {
        SortedMap<String, AccessionGroup> groupMap = new TreeMap<>();
        accesionNumbers.forEach(accessionNumber -> {
            String groupCode = accessionNumber.getGroupCode();
            if (groupMap.containsKey(groupCode)) {
                 groupMap.get(groupCode).add(accessionNumber);
            } else {
                groupMap.put(groupCode, new AccessionGroup(accessionNumber));
            }
        });
        return groupMap;
    }

}
