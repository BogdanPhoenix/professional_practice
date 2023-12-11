package org.university.business_logic.statistics;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics {
    private Statistics() {}

    public static @NotNull Map<Object, Long> createTableFrequencyData(@NotNull List<Object> data){
        Map<Object, Long> frequency = new HashMap<>();

        for(var element : data){
            frequency.put(element, frequency.getOrDefault(element, 0L) + 1L);
        }

        return frequency;
    }
}
