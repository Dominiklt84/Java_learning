package lt.viko.eif.dalencinovic.first.spring.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.springframework.stereotype.Service;

@Service
public class XMLTransformationService {
    public void transformToXML(Object object){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(object, System.out);
        }catch (JAXBException e){
            System.out.println(e.getLocalizedMessage()+e.getCause());
        }
    }
}
