import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.json.JSONObject;

import java.util.Set;

public class XMLHandler extends DefaultHandler {
    private Set<String> selectedFields;
    private JSONObject jsonObject;
    private StringBuilder currentValue;
    private boolean inRecord;

    public XMLHandler(Set<String> selectedFields) {
        this.selectedFields = selectedFields;
        this.jsonObject = new JSONObject();
        this.currentValue = new StringBuilder();
        this.inRecord = false;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("record")) {
            jsonObject = new JSONObject();
            inRecord = true;
        }
        currentValue.setLength(0);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        currentValue.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (inRecord) {
            if (qName.equalsIgnoreCase("record")) {
                System.out.println(jsonObject.toString(4));
                inRecord = false;
            } else if (selectedFields.contains(qName)) {
                jsonObject.put(qName, currentValue.toString().trim());
            }
        }
    }
}
