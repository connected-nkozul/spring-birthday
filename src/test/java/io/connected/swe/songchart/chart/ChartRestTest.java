package io.connected.swe.songchart.chart;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ChartRest.class)
public class ChartRestTest {

    @MockBean
    ChartService chartService;

    @MockBean
    ChartRepository chartRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    Hit hit1, hit2, hit3;
    Chart chart;

    @BeforeEach
    void setup() {
        hit1 = Hit.builder()
                .artist("artist1")
                .song("title1")
                .rank(1)
                .build();
        hit2 = Hit.builder()
                .artist("artist2")
                .song("title2")
                .rank(2)
                .build();
        hit3 = Hit.builder()
                .artist("artist3")
                .song("title3")
                .rank(3)
                .build();

        chart = Chart.builder()
                .weekId(LocalDate.parse("1959-12-05"))
                .url("url")
                .hit(hit1)
                .hit(hit3)
                .hit(hit2)
                .build();
    }

    @Test
    void givenValidDate_whenGetChartByDate_thenReturnChart() throws Exception {
        LocalDate date = LocalDate.parse("1959-12-05");
        Mockito.when(chartService.chartByDate(date))
                .thenReturn(Optional.of(chart));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/chart")
                .param("date", date.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ArgumentCaptor<LocalDate> dateCaptor = ArgumentCaptor.forClass(LocalDate.class);
        Mockito.verify(chartService, Mockito.times(1))
                .chartByDate(dateCaptor.capture());
        assertThat(dateCaptor.getValue().getDayOfMonth()).isEqualTo(5);

        String jsonResponse = result.getResponse().getContentAsString();
        assertThat(jsonResponse).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(chart)
        );

    }

    @Test
    void givenInvalidDate_whenGetChartByDate_thenReturnIsNotFound() throws Exception {
        LocalDate date = LocalDate.parse("1959-12-05");
        Mockito.when(chartService.chartByDate(date))
                .thenReturn(Optional.empty());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/chart")
                .param("date", date.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        ArgumentCaptor<LocalDate> dateCaptor = ArgumentCaptor.forClass(LocalDate.class);
        Mockito.verify(chartService, Mockito.times(1))
                .chartByDate(dateCaptor.capture());
        assertThat(dateCaptor.getValue().getDayOfMonth()).isEqualTo(5);

    }

}
