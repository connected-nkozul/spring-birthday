package io.connected.swe.songchart.birthdayHit;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.connected.swe.songchart.chart.ChartNotFoundException;
import io.connected.swe.songchart.chart.ChartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BirthdayHitRest.class)
public class BirthdayHitRestTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ChartRepository chartRepository;

    @MockBean
    BirthdayHitService service;

    BirthdayHitRest sut;

    LocalDate birthdayDate;
    BirthdayDto birthdayDto;

    @BeforeEach
    void setUp() {
        sut = new BirthdayHitRest(service);
        birthdayDate = LocalDate.now()
                .withYear(1982)
                .withMonth(12)
                .withDayOfMonth(6);
        birthdayDto = BirthdayDto.builder()
                .name("Name")
                .birthday(birthdayDate)
                .build();
    }

    @Test
    void test_insertBirthday_givenValidNameAndBirthday_insertAndReturnsId() {
        try {
            Mockito.when(service.saveBirthday(Mockito.any(BirthdayDto.class))).thenReturn(22L);

            try {
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/birthday")
                        .content(birthdayDto.asJson())
                        .param("date", birthdayDate.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isCreated())
                        .andReturn();
                String content = result.getResponse().getContentAsString();
                assertEquals(22, Long.parseLong(content));
            } catch (Exception e) {
                fail();
            }
        } catch (ChartNotFoundException e) {
            fail();
        }
    }

    @Test
    void test_insertBirthday_givenChartNotFoundException_returns_internalServerError() {
        try {
            Mockito.when(service.saveBirthday(Mockito.any(BirthdayDto.class))).thenThrow(new ChartNotFoundException());

            try {
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/birthday")
                        .content(birthdayDto.asJson())
                        .param("date", birthdayDate.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                        .andReturn();
                String content = result.getResponse().getContentAsString();
                assertTrue(content.isEmpty());
            } catch (Exception e) {
                fail();
            }
        } catch (ChartNotFoundException e) {
            fail();
        }
    }

}
