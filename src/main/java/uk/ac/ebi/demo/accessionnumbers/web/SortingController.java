package uk.ac.ebi.demo.accessionnumbers.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sorter")
public class SortingController {

    @PostMapping
    public void sort() {

    }

}
