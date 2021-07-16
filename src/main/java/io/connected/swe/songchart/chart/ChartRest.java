package io.connected.swe.songchart.chart;

import io.swagger.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Optional;

@Api(value = "ChartRest")
@RestController
public class ChartRest {

    final ChartService chartService;

    public ChartRest(ChartService chartService) {
        this.chartService = chartService;
    }

    /**
     * Get Chart by date.
     * @param date format yyyy-MM-dd
     */
    @ApiOperation(value = "Get Chart by date")
    @GetMapping("/api/chart")
    public Chart getChartByDate(@RequestParam
                                    @ApiParam(name = "date", value = "User's birthday", required = true,
                                            example = "YYYY-MM-DD")
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        Optional<Chart> response = chartService.chartByDate(date);
        return response.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chart not found"));
    }


}
