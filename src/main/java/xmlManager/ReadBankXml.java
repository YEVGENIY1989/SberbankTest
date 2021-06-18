package xmlManager;

import dao.Person;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;


public class ReadBankXml {


    private ArrayList<Person> persons = new ArrayList<>();
    private String walletValue;
    private BigDecimal wallet;

    public ReadBankXml(String fileName){


        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File("src/resources/xmlFile/" + fileName));



            walletValue = document.getDocumentElement().getAttribute("wallet");
            wallet = new BigDecimal(walletValue);


            NodeList personElement = document.getDocumentElement().getElementsByTagName("Person");

            for(int i = 0; i < personElement.getLength(); i++){
                Node personNode = personElement.item(i);
                NamedNodeMap attributes = personNode.getAttributes();

                String wallet = attributes.getNamedItem("wallet").getNodeValue();
                BigDecimal value = new BigDecimal(wallet);
                String namePerson = attributes.getNamedItem("name").getNodeValue();

                Person person = new Person(namePerson, value);
                persons.add(person);

            }




        }
        catch (SAXException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }


    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public BigDecimal getWallet() {
        return wallet;
    }
}
