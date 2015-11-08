package us.sodiumlabs.xmas;

import us.sodiumlabs.xmas.data.OrderMaintainer;
import us.sodiumlabs.xmas.data.XmasPayload;
import us.sodiumlabs.xmas.data.XmasResult;
import us.sodiumlabs.xmas.marshaller.HtmlMarshaller;
import us.sodiumlabs.xmas.marshaller.OutputMarshaller;
import us.sodiumlabs.xmas.marshaller.ProhibitionMarshaller;
import us.sodiumlabs.xmas.matcher.XmasMatcher;
import us.sodiumlabs.xmas.parser.NamesParser;
import us.sodiumlabs.xmas.parser.ProhibitionParser;
import us.sodiumlabs.xmas.parser.XmasParser;

import java.util.Calendar;

/**
 * @author Alex on 11/4/2015.
 */
public class XmasHats {
    public static void main(final String[] args) {
        new XmasHats().execute();
    }

    public void execute() {
        final OrderMaintainer maintainer = new OrderMaintainer();

        final XmasPayload payload = parseFiles(maintainer);
        final XmasResult result = match(payload);
        write(result, maintainer);
    }

    private XmasPayload parseFiles(final OrderMaintainer maintainer) {
        final XmasParser parser = new XmasParser(
                        new ProhibitionParser(previousYearFile()),
                        new ProhibitionParser("data/prohibited.csv"),
                        new NamesParser("data/names.txt", maintainer) );

        return parser.parse();
    }

    private XmasResult match(final XmasPayload payload) {
        return new XmasMatcher().match(payload);
    }

    private void write(final XmasResult result, final OrderMaintainer maintainer) {
        final OutputMarshaller<XmasResult> nextYearMarshaller = new ProhibitionMarshaller(thisYearFile());
        final OutputMarshaller<XmasResult> xmasMarshaller = new HtmlMarshaller(thisYearHtml());

        nextYearMarshaller.marshal(result, maintainer);
        xmasMarshaller.marshal(result, maintainer);
    }

    private String previousYearFile() {
        return "data/prohibited." + (Calendar.getInstance().get(Calendar.YEAR) - 1) + ".csv";
    }

    private String thisYearFile() {
        return "data/prohibited." + Calendar.getInstance().get(Calendar.YEAR) + ".csv";
    }

    private String thisYearHtml() {
        return "data/xmas." + Calendar.getInstance().get(Calendar.YEAR) + ".html";
    }
}
