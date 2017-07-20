package uk.ac.ebi.demo.accessionnumbers.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers=SortingController.class)
public class SortingControllerTest {

    @Autowired
    private MockMvc webApp;

    @Test
    public void testSort() throws Exception {
        //given:
        String data = String.join(", ", Arrays.asList("A00000", "ERR000112", "ERR000113",
                "SRR211001"));

        //when:
        MvcResult result = webApp
                .perform(post("/sorter")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(data))
                .andReturn();

        //then:
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    }

}
