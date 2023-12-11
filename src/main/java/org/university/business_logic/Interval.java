package org.university.business_logic;

import java.sql.Timestamp;

public class Interval {
    private final String from;
    private final String to;

    public Interval(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public Timestamp fromTimestamp() {
        return Timestamp.valueOf(from);
    }

    public Timestamp toTimestamp() {
        return Timestamp.valueOf(to);
    }
}
