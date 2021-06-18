package xmlManager;

import dao.Person;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class CreateBankXml {


    public CreateBankXml(List<Person> persons, List<Person> minAppendFromBankPersons){


        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;

        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Document document = documentBuilder.newDocument();
        String root = "total";
        Element rootElement = document.createElement(root);
        document.appendChild(rootElement);
        String result = "result";
        Element emResult = document.createElement(result);
        for(int i = 0; i < persons.size(); i++){
            String personName = "Person";
            Element emPerson = document.createElement(personName);
            String name = persons.get(i).getName();
            emPerson.setAttribute("name", name);
            BigDecimal wallet = persons.get(i).getWallet();
            emPerson.setAttribute("wallet", String.valueOf(wallet));
            BigDecimal appendFromBank = persons.get(i).getAppendFromBank();
            emPerson.setAttribute("аppendFromBank", String.valueOf(appendFromBank));
            emResult.appendChild(emPerson);
        }

        rootElement.appendChild(emResult);
        String minimum = "minimum";
        Element emMinimum = document.createElement(minimum);
        for(int i = 0; i < minAppendFromBankPersons.size(); i++){
            String personName = "Person";
            Element emPerson = document.createElement(personName);
            String name = minAppendFromBankPersons.get(i).getName();
            emPerson.setAttribute("name", name);
            emMinimum.appendChild(emPerson);
        }
        rootElement.appendChild(emMinimum);
        try{
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount","2");
            DOMSource source = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new FileWriter("src/resources/xmlFile/appendFromBank.xml"));
            transformer.transform(source,streamResult);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        catch (TransformerException e) {
            e.printStackTrace();
        }

    }

}
