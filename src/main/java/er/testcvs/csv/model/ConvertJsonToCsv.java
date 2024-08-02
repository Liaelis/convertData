package er.testcvs.csv.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class ConvertJsonToCsv {

    public void converToCsv(List<Map<String, String>> dataList) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonArray = mapper.writeValueAsString(dataList);
        JsonNode jsonTree = mapper.readTree(jsonArray);

        // Cria o arquivo CSV
        FileWriter csvOutputFile = new FileWriter("output3.csv");
        CSVFormat csvFormat = CSVFormat.DEFAULT
                .withQuoteMode(null)
                .withEscape('\\').withRecordSeparator('\n');
        CSVPrinter csvPrinter = new CSVPrinter(csvOutputFile, csvFormat);


        JsonNode firstObject = jsonTree.elements().next();
        Iterator<String> fieldNames = firstObject.fieldNames();
        while (fieldNames.hasNext()) {
            csvPrinter.print(fieldNames.next());
        }
        csvPrinter.println();

        // Itera sobre os objetos JSON e escreve cada campo no CSV
        for (JsonNode jsonNode : jsonTree) {
            Iterator<String> fieldNamesIterator = firstObject.fieldNames();
            while (fieldNamesIterator.hasNext()) {
                String fieldName = fieldNamesIterator.next();
                JsonNode valueNode = jsonNode.get(fieldName);
                csvPrinter.print(valueNode != null ? valueNode.asText() : "");
            }
            csvPrinter.println();
        }

        csvPrinter.flush();
        csvPrinter.close();
    }
}
