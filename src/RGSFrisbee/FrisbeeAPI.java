package RGSFrisbee;

import FrisbeeDatagram.*;
import RGSCommonUtils.ConnectionInterface;
import RGSCommonUtils.String4CFT;

import javax.xml.bind.*;
import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.sql.Clob;
import java.sql.SQLException;

import static RGSCommonUtils.oraDAO.String2Clob;




/**
 * Created by p.chavdarov on 20/01/2017.
 */
public class FrisbeeAPI {

    private static ConnectionInterface FBconn;

    private static void Init() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        if(FBconn == null) {
            FBconn = new FBConnection();
            FBconn.setTarget("SRV-FLEKASSIR2.erc-fl.ru", 3056, "https");
//            FBconn.setProxy("127.0.0.1", 8090, "http");
//            FBconn.setProxy("10.95.5.19", 8090, "http");
            FBconn.setProxy("10.95.17.46", 8080, "http");
            FBconn.initConnection("/RGSFrisbee/cacerts","changeit");
//            System.setProperty ("javax.net.ssl.trustStore", "D:/WORK/p.chavdarov/work/MHR/01.17/MHR-5842(Frisbee)/certs/cacerts");
//            System.setProperty ("javax.net.ssl.trustStore", "/usr/oracle/ora_fio/test2/import/frisbee/cacerts");

//            System.setProperty ("javax.net.ssl.trustStoreType", "jks");
//            System.setProperty ("javax.net.ssl.trustStorePassword", "changeit");
        }
    }

    private static Clob SendRequest(Clob request) throws Exception {
        Clob clobResponse = null;
        char[] cbuf = new char[1];
        String strRequest = "";
        String strResponse = "";
        try {
            FBconn = new FBConnection();

            clobResponse = FBconn.POST_RequestDBClob("/", request);
        }catch(Exception e){
            e.printStackTrace();
            strResponse = String4CFT.setPar(strResponse,"error", e.getMessage());
            if (strResponse.length()>=1000)
                strResponse = strResponse.substring(0, 1000);
            else
                strResponse = String.format("%-1000s", strResponse);

            clobResponse = String2Clob(strResponse);
        }

        return clobResponse;
    }

    private static JAXBElement<Request> initServicesRequest(int detalization, boolean... includes){
        ObjectFactory xml_factory = new ObjectFactory();
        GetDirectoryRequest dir_req = xml_factory.createGetDirectoryRequest();
        dir_req.setServicesDetalization(detalization);
        int i = 0;
        dir_req.setIncludeServiceTypes(includes[i++]);
//        dir_req.setIncludeRegions(includes[i++]);
//        dir_req.setIncludeContracts(includes[i++]);
//        dir_req.setIncludeOperators(includes[i++]);
//        dir_req.setIncludeCurrencies(includes[i++]);
//        dir_req.setIncludeNominals(includes[i++]);

        return xml_factory.createRequest(dir_req);
    }


//    public static GetDirectoryResponse getDirectoryRequest(int servicesDetalization){
//        String xml = "";
//        GetDirectoryResponse dir_resp = null;
//        // inin request
////        ObjectFactory xml_factory = new ObjectFactory();
////        GetDirectoryRequest dir_req = xml_factory.createGetDirectoryRequest();
////        dir_req.setServicesDetalization(servicesDetalization);
////        dir_req.setIncludeRegions(true);
////        dir_req.setIncludeServiceTypes(true);
////        dir_req.setIncludeContracts(true);
////        dir_req.setIncludeOperators(true);
////        dir_req.setIncludeCurrencies(true);
////        dir_req.setIncludeNominals(true);
//        JAXBElement<Request> req = initServicesRequest(servicesDetalization,false);//xml_factory.createRequest(dir_req);
//        // marshaling
//        try {
//            JAXBContext jc = JAXBContext.newInstance( "FrisbeeDatagram" );
//            Marshaller m = jc.createMarshaller();
//            StringWriter writer = new StringWriter();
//            m.marshal(req, writer);
//            xml = writer.toString();
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Request:");
//        System.out.println(xml);
//        // send request
//        InputStream respStream = null;
//        try {
//            Init();
//            respStream = FBconn.POST_RequestStream("/", xml);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//        // unmarshaling
//        try {
//            JAXBContext jc = JAXBContext.newInstance( "FrisbeeDatagram" );
//            Unmarshaller unmarshaller = jc.createUnmarshaller();
//            JAXBElement<Response> elm = (JAXBElement<Response>) unmarshaller.unmarshal(respStream);
//            dir_resp = (GetDirectoryResponse)elm.getValue();
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
////        dir_resp.getFullServices().getService().get(0).getParameters().getParameter().get(0).getClass()
//        return dir_resp;
//    }

    public static void getServicesToFile(int servicesDetalization) {
        String xml = "";
        GetDirectoryResponse dir_resp = null;
        // inin request
//        ObjectFactory xml_factory = new ObjectFactory();
//        GetDirectoryRequest dir_req = xml_factory.createGetDirectoryRequest();
//        dir_req.setServicesDetalization(servicesDetalization);
//        dir_req.setIncludeRegions(true);
//        dir_req.setIncludeServiceTypes(true);
//        dir_req.setIncludeContracts(true);
//        dir_req.setIncludeOperators(true);
//        dir_req.setIncludeCurrencies(true);
//        dir_req.setIncludeNominals(true);
        JAXBElement<Request> req = initServicesRequest(servicesDetalization,false);//xml_factory.createRequest(dir_req);
        // marshaling
        try {
            JAXBContext jc = JAXBContext.newInstance( "FrisbeeDatagram" );
            Marshaller m = jc.createMarshaller();
            StringWriter writer = new StringWriter();
            m.marshal(req, writer);
            xml = writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        System.out.println("Request:");
        System.out.println(xml);
        // send request
        InputStream respStream = null;
        try {
            Init();
            respStream = FBconn.POST_RequestStream("/", xml);
        }catch(Exception e){
            e.printStackTrace();
        }

        ReadableByteChannel rbc =  Channels.newChannel(respStream);
        try {
            FileOutputStream fos = new FileOutputStream("directoryresponce.xml");
            int filePosition = 0;
            Long transferedBypes = fos.getChannel().transferFrom(rbc,filePosition, Long.MAX_VALUE);
            while(transferedBypes == Long.MAX_VALUE){
                filePosition += transferedBypes;
                transferedBypes = fos.getChannel().transferFrom(rbc,filePosition, Long.MAX_VALUE);
            }
            respStream.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static Clob getServicesToClob(int servicesDetalization) throws Exception {
        Init();
        String xml ="";
        JAXBElement<Request> req = initServicesRequest(servicesDetalization,false);//xml_factory.createRequest(dir_req);
        // marshaling
        try {
            JAXBContext jc = JAXBContext.newInstance( "FrisbeeDatagram" );
            Marshaller m = jc.createMarshaller();
            StringWriter writer = new StringWriter();
            m.marshal(req, writer);
            xml = writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        System.out.println("Request:");
        System.out.println(xml);
        return FBconn.POST_RequestDBClob("/", xml);
    }

    public static String execRequestStr( String xmlRequest) throws Exception {
        Init();
        return FBconn.POST_Request("/", xmlRequest);
    }

    public static Clob execRequestClob( String xmlRequest) throws IOException, SQLException {
        return FBconn.POST_RequestDBClob("/", xmlRequest);
    }

}
