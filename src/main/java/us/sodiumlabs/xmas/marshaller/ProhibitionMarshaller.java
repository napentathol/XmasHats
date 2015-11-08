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
import java.util.Map;

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
    public boolean marshal(final XmasResult xmasResult, OrderMaintainer maintainer) {
        try(final CSVPrinter printer = new CSVPrinter(new FileWriter(file), CSVFormat.RFC4180)){
            for(Map.Entry<String, List<String>> results : xmasResult.getThisYearExclusion().entrySet()) {
                final String[] recordValues = new String[results.getValue().size() + 1];

                recordValues[0] = results.getKey();

                for(int i = 0; i < 2 && i < results.getValue().size(); i++) {
                    recordValues[i + 1] = results.getValue().get(i);
                }

                printer.printRecord(Arrays.asList(recordValues));
            }

            printer.flush();
        } catch (IOException e) {
            logger.warn("Failed to log xmas result\n[{}]", xmasResult);
            logger.fatal(e);

            return false;
        }

        return true;
    }
}
