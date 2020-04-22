package de.bitrecycling.timeshizz.common;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class NlpTime {

    /**
     * returns a localdatetime if parseable, emtpy if not (or given string is empty)
     * @param timeString
     * @return
     */
    public static Optional<LocalDateTime> parseTimeFromNlpString(String timeString) {
        if(StringUtils.isEmpty(timeString)){
            return Optional.empty();
        }
        try{
            PrettyTimeParser prettyTimeParser = new PrettyTimeParser();
            List<Date> parse = prettyTimeParser.parse(timeString);
            LocalDateTime parsedTime = parse.get(0).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            return Optional.of(parsedTime);    
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
