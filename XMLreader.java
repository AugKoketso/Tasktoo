import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.Scanner;
import org.json.JSONObject;

public class XMLreader {
    public static void main(String[] args) {
        try {
            // Specify the file path relative to the current directory
            File inputFile = new File("data.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            
            // Prompt the user to select fields
            Scanner scanner = new Scanner(System.in);
            System.out.println("Select the fields to display (comma separated): name, postalZip, region, country, address, list");
            String[] selectedFields = scanner.nextLine().split(",");
            
            for (int i = 0; i < selectedFields.length; i++) {
                selectedFields[i] = selectedFields[i].trim();
            }
            
            NodeList nList = doc.getElementsByTagName("record");
            
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    JSONObject jsonObject = new JSONObject();
                    
                    for (String field : selectedFields) {
                        switch (field) {
                            case "name":
                                jsonObject.put("name", eElement.getElementsByTagName("name").item(0).getTextContent());
                                break;
                            case "postalZip":
                                jsonObject.put("postalZip", eElement.getElementsByTagName("postalZip").item(0).getTextContent());
                                break;
                            case "region":
                                jsonObject.put("region", eElement.getElementsByTagName("region").item(0).getTextContent());
                                break;
                            case "country":
                                jsonObject.put("country", eElement.getElementsByTagName("country").item(0).getTextContent());
                                break;
                            case "address":
                                jsonObject.put("address", eElement.getElementsByTagName("address").item(0).getTextContent());
                                break;
                            case "list":
                                jsonObject.put("list", eElement.getElementsByTagName("list").item(0).getTextContent());
                                break;
                            default:
                                System.out.println("Invalid field: " + field);
                        }
                    }
                    
                    System.out.println(jsonObject.toString(4)); // Pretty print JSON with an indent factor of 4
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
