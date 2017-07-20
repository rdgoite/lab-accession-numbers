package uk.ac.ebi.demo.accessionnumbers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.demo.accessionnumbers.AccessionNumber;
import uk.ac.ebi.demo.accessionnumbers.Sorter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sorter")
public class SortingController {

    @Autowired
    private Sorter sorter;

    @PostMapping
    public void sort(@RequestBody String request) {
        String[] data = request.split(",");
        List<AccessionNumber> inputs = Arrays.stream(data)
                .map(AccessionNumber::parse)
                .collect(Collectors.toList());
        sorter.sort(inputs);
    }

}
