package us.sodiumlabs.xmas.marshaller;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import us.sodiumlabs.xmas.data.OrderMaintainer;
import us.sodiumlabs.xmas.data.XmasResult;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import static java.util.Objects.requireNonNull;

/**
 * @author Alex on 11/5/2015.
 */
public class HtmlMarshaller implements OutputMarshaller<XmasResult> {
    private static final Logger logger = LogManager.getLogger(HtmlMarshaller.class);
    private final String file;
    private final String TITLE = "&#x1F384; Maurer Christmas Exchange &#x2603;";
    public HtmlMarshaller(final String file) {
        this.file = requireNonNull(file);
    }

    @Override
    public boolean marshal(final XmasResult xmasResult, final OrderMaintainer maintainer, final int year) {
        try(final Writer writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            writeHtml(writer, xmasResult, maintainer, year);
        } catch (final IOException ex) {
            logger.warn("Failed to write result to html [{}]", xmasResult);
            logger.fatal(ex);

            return false;
        }

        return true;
    }

    private void writeHtml(final Writer writer, final XmasResult result, final OrderMaintainer maintainer, final int year) throws IOException {
        writer.write("<html>");
        writeHead(writer);
        writeBody(writer, result, maintainer, year);
        writer.write("</html>");
    }

    private void writeHead(final Writer writer) throws IOException {
        writer.write("<head>");
        writeStyle(writer);
        writer.write("</head>");
    }

    private void writeStyle(final Writer writer) throws IOException {
        writer.write("<title>");
        writer.write(TITLE);
        writer.write("</title>");
        writer.write("<link href='https://fonts.googleapis.com/css?family=Ubuntu' rel='stylesheet' type='text/css'>");
        writer.write("<style>table { border-spacing: 0; font-family: 'Ubuntu', sans-serif; font-size: xx-large; text-shadow: 1px 1px #888; }</style>");
        writer.write("<style>th, td { width: 20%;}</style>");
        writer.write("<style>th { text-align: left; padding-left: 10px; padding-top: 4px; padding-bottom: 4px; }</style>");
        writer.write("<style>td { padding-left: 15px; padding-top: 2px; padding-bottom: 2px; }</style>");
        writer.write("<style>tr:nth-child(even) { background-color: #B2303a; color: #000 }</style>");
        writer.write("<style>tr:nth-child(odd) { background-color: #228422; color: #111; }</style>");
        writer.write("<style>tr:hover { filter: brightness(150%) }</style>");
        writer.write("<style>html { background-color: #999; }</style>");
    }

    private void writeBody(final Writer writer, final XmasResult result, final OrderMaintainer maintainer, final int year) throws IOException {
        writer.write("<body>");
        writer.write("<h1>");
        writer.write(Integer.toString(year));
        writer.write(" ");
        writer.write(TITLE);
        writer.write("</h1>");
        writeTable(writer, result, maintainer);
        writer.write("</body>");
    }

    private void writeTable(final Writer writer, final XmasResult result, final OrderMaintainer maintainer) throws IOException {
        writer.write("<table>");
        writeTableHead(writer);
        for(final String name : maintainer.getNames()) {
            writeEntry(writer, name, result.matchMap().get(name));
        }
        writer.write("</table>");
    }

    private void writeTableHead(final Writer writer) throws IOException {
        writer.write("<tr><th>&#x1F385; This Person:</th><th>&#x1F936; Gives to:</th></tr>");
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
                WordUtils.capitalize(value.toLowerCase()) ));
        writer.write("</td>");
    }
}
