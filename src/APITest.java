import RGSFrisbee.FrisbeeAPI;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;


/**
 * Created by Павел on 26.01.2017.
 */
public class APITest {

    public static void main(String[] args){

//        String xml = "";
//
//        ObjectFactory xml_factory = new ObjectFactory();
//        GetDirectoryRequest dir_req = xml_factory.createGetDirectoryRequest();
//        dir_req.setServicesDetalization(4);
//        dir_req.setIncludeRegions(true);
//        dir_req.setIncludeServiceTypes(true);
//        dir_req.setIncludeContracts(true);
//        dir_req.setIncludeOperators(true);
//        dir_req.setIncludeCurrencies(true);
//        dir_req.setIncludeNominals(true);
//        JAXBElement<Request> req = xml_factory.createRequest(dir_req);
//
//        try {
//            JAXBContext jc = JAXBContext.newInstance( "RGSFrisbee.FrisbeeDatagram" );
//            Marshaller m = jc.createMarshaller();
//            StringWriter writer = new StringWriter();
//            m.marshal(req, writer);
//            xml = writer.toString();
//            System.out.println(xml);
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
        //xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Request xsi:type=\"GetDirectoryRequest\" ServicesDetalization=\"1\" xmlns=\"http://ekassir.com/eKassir/PaySystem/Server/eKassirV3Protocol\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>";
//        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
//                "<Request xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xsi:type=\"GetDirectoryRequest\" S" +
//                "ervicesDetalization=\"4\" IncludeRegions=\"true\" IncludeServiceTypes=\"true\" IncludeContrac" +
//                "ts=\"true\" IncludeOperators=\"true\" IncludeCurrencies=\"true\" IncludeNominals=\"true\" xmlns" +
//                "=\"http://ekassir.com/eKassir/PaySystem/Server/eKassirV3Protocol\" />";
//        System.out.println("Responce:");
//        System.out.println(FrisbeeAPI.SendRequest(xml));

//        FrisbeeAPI.getDirectoryRequest(4);
        FrisbeeAPI.getServicesToFile(4);
//        try {
//            FrisbeeAPI.execRequestStr("<?xml version=\"1.0\" encoding=\"windows-1251\"?>\n" +
//                    "         <Request xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://ekassir.com/eKassir/PaySystem/Server/eKassirV3Protocol\" xsi:type=\"RegisterCheckRequest\" Id=\"52475342-10-02-17-085100\" Service=\"4\">\n" +
//                    "           <PaymentParameters>\n" +
//                    "             <Parameter Name=\"NAME\" Value=\"VALUE\"/>\n" +
//                    "           </PaymentParameters>\n" +
//                    "         </Request>");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String foo = "07-01-2017";
//        byte[] bytes = foo.getBytes();
//        System.out.println( Hex.encodeHexString( bytes ) );
//        System.out.println( String.format("%s-%s-%s-%s-%s",
//                                            Hex.encodeHexString("2665".getBytes()),
//                                            Hex.encodeHexString("07".getBytes()),
//                                            Hex.encodeHexString("02".getBytes()),
//                                            Hex.encodeHexString("17".getBytes()),
//                                            Hex.encodeHexString("130115".getBytes())
//                                        )
//        );

    }
}
