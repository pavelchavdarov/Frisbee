import RGS_FRISBEE.FrisbeeAPI;
import RGS_FRISBEE.frisbee_datagram.GetDirectoryRequest;
import RGS_FRISBEE.frisbee_datagram.ObjectFactory;
import RGS_FRISBEE.frisbee_datagram.Request;
import com.sun.xml.internal.bind.v2.runtime.MarshallerImpl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 * Created by Павел on 26.01.2017.
 */
public class APITest {

    public static void main(String[] args){
//        String xml =    "<?xml version=\"1.0\" encoding=\"utf-8\"?> " +
//                        "<Request xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" " +
////                        "<Request xmlns:xsi=\"http://ekassir.com/eKassir/PaySystem/Server/eKassirV3Protocol\" " +
//                            "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
//                            "xsi:type=\"GetDirectoryRequest\" " +
//                            "ServicesDetalization=\"4\" " +
//                            "IncludeRegions=\"true\" " +
//                            "IncludeServiceTypes=\"true\" " +
//                            "IncludeContracts=\"true\" " +
//                            "IncludeOperators=\"true\" " +
//                            "IncludeCurrencies=\"true\" " +
//                            "IncludeNominals=\"true\" " +
//                            "xmlns =\"http://ekassir.com/eKassir/PaySystem/Server/eKassirV3Protocol\" />";

        String xml = "";

        ObjectFactory xml_factory = new ObjectFactory();
        GetDirectoryRequest dir_req = xml_factory.createGetDirectoryRequest();
        dir_req.setServicesDetalization(2);
//        dir_req.setIncludeRegions(true);
        dir_req.setIncludeServiceTypes(true);
//        dir_req.setIncludeContracts(true);
//        dir_req.setIncludeOperators(true);
//        dir_req.setIncludeCurrencies(true);
//        dir_req.setIncludeNominals(true);
        JAXBElement<Request> req = xml_factory.createRequest(dir_req);

        try {
            JAXBContext jc = JAXBContext.newInstance( "RGS_FRISBEE.frisbee_datagram" );
            Marshaller m = jc.createMarshaller();
            StringWriter writer = new StringWriter();
            m.marshal(req, writer);
            xml = writer.toString();
            System.out.println(xml);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        //xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Request xsi:type=\"GetDirectoryRequest\" ServicesDetalization=\"1\" xmlns=\"http://ekassir.com/eKassir/PaySystem/Server/eKassirV3Protocol\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>";
//        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
//                "<Request xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xsi:type=\"GetDirectoryRequest\" S" +
//                "ervicesDetalization=\"4\" IncludeRegions=\"true\" IncludeServiceTypes=\"true\" IncludeContrac" +
//                "ts=\"true\" IncludeOperators=\"true\" IncludeCurrencies=\"true\" IncludeNominals=\"true\" xmlns" +
//                "=\"http://ekassir.com/eKassir/PaySystem/Server/eKassirV3Protocol\" />";
        System.out.println("Responce:");
        System.out.println(FrisbeeAPI.SendRequest(xml));
    }
}
