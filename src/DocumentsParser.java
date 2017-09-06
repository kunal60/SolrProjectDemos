
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.jpeg.JpegParser;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class DocumentsParser {

	public static HashMap<String, String> parse(String fileName, String fileType)
			throws IOException, TikaException, SAXException {
		// detecting the file type
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		FileInputStream inputstream = new FileInputStream(new File(fileName));
		ParseContext pcontext = new ParseContext();

		if (fileType.equalsIgnoreCase("pdf")) {
			PDFParser pdfparser = new PDFParser();
			pdfparser.parse(inputstream, handler, metadata, pcontext);
		}

		if (fileType.equalsIgnoreCase("jpg")) {
			JpegParser JpegParser = new JpegParser();
			JpegParser.parse(inputstream, handler, metadata, pcontext);
		}

		if (fileType.equalsIgnoreCase("xlsx")) {
			OOXMLParser msofficeparser = new OOXMLParser();
			msofficeparser.parse(inputstream, handler, metadata, pcontext);
		}

		String[] metadataNames = metadata.names();

		HashMap<String, String> map = new HashMap<>();
		map.put("Contents of the PDF", handler.toString());
		for (String name : metadataNames) {
			map.put(name, metadata.get(name));
		}

		return map;
	}

}