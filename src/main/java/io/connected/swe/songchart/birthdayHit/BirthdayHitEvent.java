package io.connected.swe.songchart.birthdayHit;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class BirthdayHitEvent {
    private final BirthdayHit birthdayHit;
}
