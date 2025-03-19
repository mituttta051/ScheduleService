package cybercooker.scheduleservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WeekDay {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7);

    private final int value;

    public static WeekDay fromValue(int value) {
        for (WeekDay weekDay : WeekDay.values()) {
            if (weekDay.getValue() == value) {
                return weekDay;
            }
        }
        throw new IllegalArgumentException("Invalid day value: " + value);
    }
}
