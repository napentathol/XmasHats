package us.sodiumlabs.xmas.parser;

import us.sodiumlabs.xmas.data.NameExclusions;
import us.sodiumlabs.xmas.data.Names;
import us.sodiumlabs.xmas.data.XmasPayload;

import java.util.List;
import java.util.Map;

/**
 * @author Alex on 11/4/2015.
 */
public class XmasParser implements InputParser<XmasPayload> {
    private final InputParser<Map<String, List<String>>> previousYearParser;

    private final InputParser<Map<String, List<String>>> miscParser;

    private final InputParser<Names> namesInputParser;

    public XmasParser(InputParser<Map<String, List<String>>> previousYearParser, InputParser<Map<String, List<String>>> miscParser, InputParser<Names> namesInputParser) {
        this.previousYearParser = previousYearParser;
        this.miscParser = miscParser;
        this.namesInputParser = namesInputParser;
    }

    @Override
    public XmasPayload parse() {
        final NameExclusions exclusions = NameExclusions.builder()
            .withLastYears(previousYearParser.parse())
            .withMisc(miscParser.parse())
            .build();
        final Names names = namesInputParser.parse();

        return XmasPayload.builder()
            .withNames(names)
            .withNameExclusions(exclusions)
            .build();
    }
}
