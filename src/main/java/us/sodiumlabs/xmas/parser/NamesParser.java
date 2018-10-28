package us.sodiumlabs.xmas.parser;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import us.sodiumlabs.xmas.data.Names;
import us.sodiumlabs.xmas.data.OrderMaintainer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Alex on 11/4/2015.
 */
public class NamesParser implements InputParser<Names> {
    private static final Logger logger = LogManager.getLogger(NamesParser.class);

    private final String inputFile;

    private final OrderMaintainer maintainer;

    public NamesParser(final String inputFile, final OrderMaintainer maintainer) {
        this.inputFile = inputFile;
        this.maintainer = maintainer;
    }

    @Override
    public Names parse() {
        logger.info("Starting names parsing.");

        final Set<String> inputNames = new HashSet<>();

        try(final BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            for(String line; (line = br.readLine()) != null; ) {
                if( line.isEmpty() ) continue;

                final String inputName = readName(line);
                logger.info("Parsed input name {}", inputName);

                if(StringUtils.isNotEmpty(inputName)){
                    inputNames.add(inputName);
                    maintainer.addName(inputName);
                }
            }
        } catch (final IOException fenex) {
            logger.fatal("Failed to parse input names!", fenex);
        }

        return new Names(inputNames.toArray(new String[0]));
    }

    private String readName(final String line) {
        return line.trim().toLowerCase();
    }
}
