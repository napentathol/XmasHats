package us.sodiumlabs.xmas.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * @author Alex on 11/4/2015.
 */
public class ProhibitionParser implements InputParser<Map<String, List<String>>> {
    private static final Logger logger = LogManager.getLogger(ProhibitionParser.class);

    private final String inputFile;

    public ProhibitionParser(final String inputFile) {
        this.inputFile = inputFile;
    }


    @Override
    public Map<String, List<String>> parse() {
        final Map<String, List<String>> prohibitionMap =  new HashMap<>();

        try (final CSVParser parser = new CSVParser(new FileReader(inputFile), CSVFormat.RFC4180)) {
            parser.forEach((final CSVRecord record) -> {
                final String name = readRecord(record, 0);

                final List<String> probitions = new ArrayList<>();

                for(int i = 1; i < record.size(); i++) {
                    probitions.add( readRecord(record, i) );
                }

                prohibitionMap.put(name, probitions);
            });
        } catch (final IOException ex) {
            logger.fatal("Failed to parse prohibition file: " + inputFile, ex);
        }

        return prohibitionMap;
    }

    private String readRecord(final CSVRecord record, final int i) {
        return record.get(i).trim().toLowerCase();
    }
}
