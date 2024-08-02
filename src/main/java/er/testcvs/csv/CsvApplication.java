package er.testcvs.csv;

import er.testcvs.csv.model.ExcelXmlExtractin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CsvApplication implements CommandLineRunner {

	@Autowired
	ExcelXmlExtractin excelXmlExtractin;

	public static void main(String[] args) {
		SpringApplication.run(CsvApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		excelXmlExtractin.readAndProcessing();
	}
}
