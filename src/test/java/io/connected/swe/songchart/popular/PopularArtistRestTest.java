package io.connected.swe.songchart.popular;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.connected.swe.songchart.chart.ChartRepository;
import io.connected.swe.songchart.chart.ChartRest;
import io.connected.swe.songchart.chart.ChartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PopularArtistRest.class)
public class PopularArtistRestTest {

    @MockBean
    PopularArtistService service;

    @MockBean
    ChartRepository repository;

    @MockBean
    ChartService chartService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void test_getMostPopular_given_Artist() throws Exception {
        PopularArtist mostPopular = PopularArtist.builder()
                .artist("artist")
                .birthdays(10)
                .build();
        Mockito.when(service.findMostPopularArtist())
                .thenReturn(Optional.of(mostPopular));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/popular")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        Assertions.assertEquals(objectMapper.writeValueAsString(mostPopular), jsonResponse);
    }

}
