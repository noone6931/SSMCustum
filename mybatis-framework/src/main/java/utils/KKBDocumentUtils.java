package utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

public class KKBDocumentUtils {
    public static Document getDocument(InputStream inputStream) {
        Document document;
        SAXReader saxReader = new SAXReader();
        try {
            document = saxReader.read(inputStream);
            return document;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
