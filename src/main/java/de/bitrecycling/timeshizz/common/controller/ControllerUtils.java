package de.bitrecycling.timeshizz.common.controller;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ControllerUtils {

    public static LocalDateTime parseTime(String timeString) {
        PrettyTimeParser prettyTimeParser = new PrettyTimeParser();
        List<Date> parse = prettyTimeParser.parse(timeString);
        LocalDateTime parsedTime = parse.get(0).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        return parsedTime;
    }
    
}
