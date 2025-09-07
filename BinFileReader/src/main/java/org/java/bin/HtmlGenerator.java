package org.java.bin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class HtmlGenerator {

    public static boolean generateHtmlReport(List<Message> messages, String outputFilePath) {
        System.out.println("Generating HTML report...");

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<!DOCTYPE html>")
                .append("<html>")
                .append("<head>")
                .append("<title>Log File Report</title>")
                .append("<style>")
                .append("body { font-family: 'Inter', sans-serif; margin: 2rem; background-color: #f0f2f5; color: #343a40; }")
                .append(".report-container { width: 500px; margin: 0 auto; }")
                .append("h1 { color: #000000; text-align: center; }")
                .append("table { width: 500px; border-collapse: collapse; margin-top: 1.5rem; box-shadow: 0 4px 12px rgba(0,0,0,0.15); border-radius: 8px; overflow: hidden; }")
                .append("th, td { padding: 15px; text-align: left; border-bottom: 1px solid #dee2e6; }")
                .append(".sequence-number-cell { width: 100px; }")
                .append("thead { background-color: #007bff; color: white; }")
                .append("tbody tr:nth-child(even) { background-color: #f8f9fa; }")
                .append("tbody tr:hover { background-color: #e2e6ea; transition: background-color 0.2s; }")
                .append("</style>")
                .append("</head>")
                .append("<body>")
                .append("<div class='report-container'>")
                .append("<h1>logi.bin</h1>")
                .append("<table>")
                .append("<thead>")
                .append("<tr><th>Sequence Number</th><th>Payload</th></tr>")
                .append("</thead>")
                .append("<tbody>");

        for (Message msg : messages) {
            htmlBuilder.append("<tr>")
                    .append("<td class='sequence-number-cell'>").append(msg.getSequenceNumber()).append("</td>")
                    .append("<td>").append(msg.getPayload()).append("</td>")
                    .append("</tr>");
        }

        htmlBuilder.append("</tbody>")
                .append("</table>")
                .append("</div>")
                .append("</body>")
                .append("</html>");

        try {
            Files.writeString(Paths.get(outputFilePath), htmlBuilder.toString());
            System.out.println("HTML report generated at: " + new java.io.File(outputFilePath).getAbsolutePath());
            return true;
        } catch (IOException e) {
            System.err.println("An I/O error occurred while writing the report: " + e.getMessage());
            return false;
        }
    }
}
