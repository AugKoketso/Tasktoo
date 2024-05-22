import org.xml.sax.SAXException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class XMLreader {
    public static void main(String[] args) {
        try {
            // Prompt the user to select fields
            Scanner scanner = new Scanner(System.in);
            System.out.println("Select the fields to display (comma separated): name, postalZip, region, country, address, list");
            String[] selectedFieldsArray = scanner.nextLine().split(",");
            
            Set<String> selectedFields = new HashSet<>();
            for (String field : selectedFieldsArray) {
                field = field.trim();
                if (isValidField(field)) {
                    selectedFields.add(field);
                } else {
                    System.out.println("Invalid field: " + field);
                }
            }
            
            if (selectedFields.isEmpty()) {
                System.out.println("No valid fields selected. Exiting program.");
                return;
            }

            // Set up SAX parser
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLHandler handler = new XMLHandler(selectedFields);
            File inputFile = new File("data.xml");

            // Parse the input file
            saxParser.parse(inputFile, handler);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static boolean isValidField(String field) {
        switch (field) {
            case "name":
            case "postalZip":
            case "region":
            case "country":
            case "address":
            case "list":
                return true;
            default:
                return false;
        }
    }
}

