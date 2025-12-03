package us.sodiumlabs.xmas.parser;

import us.sodiumlabs.xmas.data.NameExclusions;
import us.sodiumlabs.xmas.data.Names;
import us.sodiumlabs.xmas.data.XmasPayload;

import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * @author Alex on 11/4/2015.
 */
public class XmasParser implements InputParser<XmasPayload> {
    private final InputParser<Map<String, List<String>>> previousYearParser;

    private final InputParser<Map<String, List<String>>> miscParser;

    private final InputParser<Names> namesInputParser;

    public XmasParser(
            final InputParser<Map<String, List<String>>> previousYearParser,
            final InputParser<Map<String, List<String>>> miscParser,
            final InputParser<Names> namesInputParser) {
        this.previousYearParser = requireNonNull(previousYearParser);
        this.miscParser = requireNonNull(miscParser);
        this.namesInputParser = requireNonNull(namesInputParser);
    }

    @Override
    public XmasPayload parse() {
        final NameExclusions exclusions = new NameExclusions(previousYearParser.parse(), miscParser.parse());
        final Names names = namesInputParser.parse();

        return new XmasPayload(names, exclusions);
    }
}
