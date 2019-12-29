package us.sodiumlabs.xmas.marshaller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import us.sodiumlabs.xmas.data.OrderMaintainer;
import us.sodiumlabs.xmas.data.XmasResult;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Alex on 11/5/2015.
 */
public class ProhibitionMarshaller implements OutputMarshaller<XmasResult> {
    private static final Logger logger = LogManager.getLogger(ProhibitionMarshaller.class);

    private final String file;

    public ProhibitionMarshaller(final String file) {
        this.file = file;
    }

    @Override
    public boolean marshal(final XmasResult xmasResult, final OrderMaintainer maintainer) {
        try(final CSVPrinter printer = new CSVPrinter(new FileWriter(file), CSVFormat.RFC4180)){

            final Stream<String> unorderedNames = xmasResult.getThisYearExclusion().keySet().stream()
                .filter(e -> !maintainer.getNames().contains(e));

            Stream.concat(maintainer.getNames().stream(), unorderedNames).forEach(name -> {
                final String[] recordValues = new String[3];

                recordValues[0] = name;
                final List<String> exclusions = xmasResult.getThisYearExclusion().get(name);

                for(int i = 0; i < 2 && i < exclusions.size(); i++) {
                    recordValues[i + 1] = exclusions.get(i);
                }

                try {
                    printer.printRecord(Arrays.asList(recordValues));
                } catch(final IOException e) {
                    throw new RuntimeException(e);
                }
            });

            printer.flush();
        } catch (final IOException | RuntimeException e) {
            logger.warn("Failed to log xmas result\n[{}]", xmasResult);
            logger.fatal(e);

            return false;
        }

        return true;
    }
}
