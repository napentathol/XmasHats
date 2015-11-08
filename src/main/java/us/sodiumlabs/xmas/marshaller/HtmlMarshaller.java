package us.sodiumlabs.xmas.marshaller;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import us.sodiumlabs.xmas.data.OrderMaintainer;
import us.sodiumlabs.xmas.data.XmasResult;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @author Alex on 11/5/2015.
 */
public class HtmlMarshaller implements OutputMarshaller<XmasResult> {
    private static final Logger logger = LogManager.getLogger(HtmlMarshaller.class);
    private final String file;

    public HtmlMarshaller(String file) {
        this.file = file;
    }

    @Override
    public boolean marshal(final XmasResult xmasResult, final OrderMaintainer maintainer) {
        try(final Writer writer = new FileWriter(file)) {
            writeHtml(writer, xmasResult, maintainer);
        } catch (final IOException ex) {
            logger.warn("Failed to write result to html [{}]", xmasResult);
            logger.fatal(ex);

            return false;
        }

        return true;
    }

    private void writeHtml(final Writer writer, final XmasResult result, final OrderMaintainer maintainer) throws IOException {
        writer.write("<html>");
        writeHead(writer);
        writeBody(writer, result, maintainer);
        writer.write("</html>");
    }

    private void writeHead(final Writer writer) throws IOException {
        writer.write("<head>");
        writeStyle(writer);
        writer.write("</head>");
    }

    private void writeStyle(final Writer writer) throws IOException {
        writer.write("<link href='https://fonts.googleapis.com/css?family=Ubuntu' rel='stylesheet' type='text/css'>");
        writer.write("<style>table { border-spacing: 0; font-family: 'Ubuntu', sans-serif; font-size: large; text-shadow: 1px 1px #888; }</style>");
        writer.write("<style>th, td { width: 20%;}</style>");
        writer.write("<style>th { text-align: left; padding-left: 10px; padding-top: 4px; padding-bottom: 4px; }</style>");
        writer.write("<style>td { padding-left: 15px; padding-top: 2px; padding-bottom: 2px; }</style>");
        writer.write("<style>tr:nth-child(even) { background-color: #B23052; color: #000 }</style>");
        writer.write("<style>tr:nth-child(odd) { background-color: #228422; color: #111; }</style>");
        writer.write("<style>html { background-color: #999; }</style>");
    }

    private void writeBody(final Writer writer, final XmasResult result, final OrderMaintainer maintainer) throws IOException {
        writer.write("<body>");
        writeTable(writer, result, maintainer);
        writer.write("</body>");
    }

    private void writeTable(final Writer writer, final XmasResult result, final OrderMaintainer maintainer) throws IOException {
        writer.write("<table>");
        writeTableHead(writer);
        for(final String name : maintainer.getNames()) {
            writeEntry(writer, name, result.getMatchMap().get(name));
        }
        writer.write("</table>");
    }

    private void writeTableHead(final Writer writer) throws IOException {
        writer.write("<tr><th>This person:</th><th>Gives to:</th></tr>");
    }

    private void writeEntry(final Writer writer, final String name, final  String match) throws IOException {
        writer.write("<tr>");
        writeCell(writer, name);
        writeCell(writer, match);
        writer.write("</tr>");
    }

    private void writeCell(final Writer writer, final String value) throws IOException {
        writer.write("<td>");
        writer.write(StringEscapeUtils.escapeHtml4(
                StringUtils.capitalize(value.toLowerCase()) ));
        writer.write("</td>");
    }
}
