package io.connected.swe.songchart.birthdayHit;

import io.connected.swe.songchart.chart.ChartNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BirthdayHitRest {

    private final BirthdayHitService service;

    public BirthdayHitRest(BirthdayHitService service) {
        this.service = service;
    }

    @PostMapping("/api/birthday")
    public ResponseEntity<Long> insertBirthday(@RequestBody BirthdayDto birthday) {
        Long id = null;
        try {
            id = service.saveBirthday(birthday);
        } catch (ChartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return new ResponseEntity<Long>(id, HttpStatus.CREATED);
    }
}
