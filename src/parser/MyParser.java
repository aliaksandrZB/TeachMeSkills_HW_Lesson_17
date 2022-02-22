package parser;

import validator.Validator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Scanner;

public class MyParser implements Parser {

    @Override
    public void parsing() {
        System.out.println("Enter the path to the folder: ");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
        scanner.close();

        File file = new File(path);
        file = Validator.validate(file);
        if (file == null) {
            return;
        }

        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(file));

            File book = new File(file.getParent() + File.separator + "My book.txt");
            book.createNewFile();
            FileWriter writerBook = new FileWriter(book);

            String authorsName = "";
            String authorsSurname = "";
            String myTitle = "";

            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();
                if (nextEvent.isStartElement()) {
                    StartElement startElement = nextEvent.asStartElement();

                    if (startElement.getName().getLocalPart().equals("firstName")) {
                        nextEvent = reader.nextEvent();
                        Characters characters = nextEvent.asCharacters();
                        authorsName = characters.getData();
                        continue;
                    }
                    if (startElement.getName().getLocalPart().equals("lastName")) {
                        nextEvent = reader.nextEvent();
                        Characters characters = nextEvent.asCharacters();
                        authorsSurname = characters.getData();
                        continue;
                    }
                    if (startElement.getName().getLocalPart().equals("title")) {
                        nextEvent = reader.nextEvent();
                        Characters characters = nextEvent.asCharacters();
                        myTitle = characters.getData();
                    }
                    if (startElement.getName().getLocalPart().equals("line")) {
                        nextEvent = reader.nextEvent();
                        Characters characters = nextEvent.asCharacters();
                        writerBook.write(characters.getData() + "\n");
                    }
                }
            }
            writerBook.close();

            File newNameBook = new File(book.getParent() + File.separator
                                        + authorsName + "_" + authorsSurname + "_" + myTitle);
            book.renameTo(newNameBook);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return;
        }

        
    }

}
