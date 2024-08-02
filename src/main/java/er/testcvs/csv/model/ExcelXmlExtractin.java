package er.testcvs.csv.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.CDL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExcelXmlExtractin {
    @Autowired
    ConvertJsonToCsv convertJsonToCsv;

    public void readAndProcessing() throws IOException, ParserConfigurationException, SAXException {
        // Carregar o arquivo Excel
        File file = new File("\\relatorio.xls");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        // Exemplo: imprimir alguns elementos do XML
        NodeList theadList = document.getElementsByTagName("thead");
        List<String> headers = new ArrayList<>();
        if (theadList.getLength() > 0) {
            Node thead = theadList.item(0);
            NodeList thList = ((Element) thead).getElementsByTagName("th");
            for (int i = 0; i < thList.getLength(); i++) {
                NodeList spanList = ((Element) thList.item(i)).getElementsByTagName("span");
                if (spanList.getLength() > 0) {
                    headers.add(spanList.item(0).getTextContent());
                }
            }
        }



        //headers.forEach(System.out::println);

        NodeList tbodyList = document.getElementsByTagName("tbody");
        List<Map<String, String>> dataList = new ArrayList<>();
        if (tbodyList.getLength() > 0) {
            Node tbody = tbodyList.item(0);
            NodeList trList = ((Element) tbody).getElementsByTagName("tr");
            for (int i = 0; i < trList.getLength(); i++) {

                Node tr = trList.item(i);
                NodeList tdList = ((Element) tr).getElementsByTagName("td");
                Map<String, String> dataMap = new HashMap<>();
                for (int j = 0; j < tdList.getLength(); j++) {
                    NodeList spanList = ((Element) tdList.item(j)).getElementsByTagName("span");
                    if (spanList.getLength() > 0) {

                        dataMap.put(headers.get(j),spanList.item(0).getTextContent() );
                        System.out.println("ISSO AQUI "+ spanList.item(0).getTextContent());
                    }
                }

                dataList.add(dataMap);
            }

        }

        // Converter a lista em JSON usando Jackson
        convertJsonToCsv.converToCsv(dataList);

      //  System.out.println(jsonArray);
        //  System.out.println(jsonArray);


    }

}
