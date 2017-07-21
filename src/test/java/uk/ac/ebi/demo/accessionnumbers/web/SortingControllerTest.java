package uk.ac.ebi.demo.accessionnumbers.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.ac.ebi.demo.accessionnumbers.AccessionNumber;
import uk.ac.ebi.demo.accessionnumbers.ConsecutiveAccessionGroup;
import uk.ac.ebi.demo.accessionnumbers.Sorter;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers=SortingController.class)
public class SortingControllerTest {

    @Autowired
    private MockMvc webApp;

    @MockBean
    private Sorter sorter;

    @Test
    public void testSort() throws Exception {
        //given:
        String data = String.join(", ", asList("A00000", "ERR000112", "ERR000113",
                "SRR211001"));

        //and:
        ConsecutiveAccessionGroup group1 = mock(ConsecutiveAccessionGroup.class);
        doReturn("A00000").when(group1).toString();
        ConsecutiveAccessionGroup group2 = mock(ConsecutiveAccessionGroup.class);
        doReturn("ERR000112-ERR000113").when(group2).toString();
        ConsecutiveAccessionGroup group3 = mock(ConsecutiveAccessionGroup.class);
        doReturn("SRR211001").when(group3).toString();

        //and:
        List<ConsecutiveAccessionGroup> results = asList(group1, group2, group3);
        doReturn(results).when(sorter).sort(anyListOf(AccessionNumber.class));

        //when:
        MvcResult result = webApp
                .perform(post("/sorter")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(data))
                .andReturn();

        //then:
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        //and:
        ArgumentCaptor<List<AccessionNumber>> inputCaptor = forClass((Class) List.class);
        verify(sorter).sort(inputCaptor.capture());
        assertThat(inputCaptor.getValue())
                .extracting("code", "number")
                .containsExactly(tuple("A", "00000"), tuple("ERR", "000112"),
                        tuple("ERR", "000113"), tuple("SRR", "211001"));

        //and:
        assertThat(response.getContentType()).contains(MediaType.TEXT_PLAIN_VALUE);
        String expectedContent = "A00000, ERR000112-ERR000113, SRR211001\n";
        assertThat(response.getContentAsString()).isEqualTo(expectedContent);
    }

}
