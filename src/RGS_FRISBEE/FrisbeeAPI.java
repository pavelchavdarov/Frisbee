package RGS_FRISBEE;

import RGS_COMMON_UTILS.ConnectionInterface;
import RGS_COMMON_UTILS.String4CFT;
import static RGS_COMMON_UTILS.oraDAO.CreateClob;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;




/**
 * Created by p.chavdarov on 20/01/2017.
 */
public class FrisbeeAPI {

    private static ConnectionInterface FBconn;

//    private static Clob CreateClob(String source) throws Exception{
//        //resStr= RGS_COMMON_UTILS.String4CFT.setPar(resStr,"state: ", source);
//        //source = String.format("%-1000s", source);
//
//        char[] cbuf = source.toCharArray();
//        System.err.println("creating clob...");
//        System.err.println("source: " + source);
//        oracle.jdbc.OracleConnection oraConn =
////                (oracle.jdbc.OracleConnection)DriverManager.getConnection("jdbc:oracle:thin:@test03.msk.russb.org:1521:rbotest2","ibs","12ibs");
//                (oracle.jdbc.OracleConnection)new OracleDriver().defaultConnection();
//
//        Clob result =  oracle.sql.CLOB.createTemporary(oraConn, true, oracle.sql.CLOB.DURATION_SESSION);
//
//        BufferedWriter bw = (BufferedWriter) result.setCharacterStream(1);
//        bw.write(cbuf);
//        bw.flush();
//        return result;
//    }
    private static void Init(){
        if(FBconn == null) {
            FBconn = new FBConnection();
            FBconn.setTarget("SRV-FLEKASSIR2.erc-fl.ru", 80, "http");
            FBconn.setProxy("127.0.0.1", 8090, "http");
//            FBconn.setProxy("10.95.5.19", 8090, "http");
//            FBconn.setProxy("10.95.17.46", 8080, "http");

        }
    }


    public static Clob SendRequest(Clob request) throws Exception {
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

            clobResponse = CreateClob(strResponse);
        }

        return clobResponse;
    }

    public static String SendRequest(String request){
        String Response = "";
//        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//                "<Request xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xsi:type=\"GetDirectoryRequest\" S\n" +
//                "ervicesDetalization=\"4\" IncludeRegions=\"true\" IncludeServiceTypes=\"true\" IncludeContrac\n" +
//                "ts=\"true\" IncludeOperators=\"true\" IncludeCurrencies=\"true\" IncludeNominals=\"true\" xmlns\n" +
//                "=\"http://ekassir.com/eKassir/PaySystem/Server/eKassirV3Protocol\" />";
//        char[] cbuf = new char[1];
//        String strRequest = "";
//        String strResponse = "";
        try {
            Init();
            Response = FBconn.POST_Request("/", request);
        }catch(Exception e){
            Response = String4CFT.setPar(Response,"error", e.getMessage());
            e.printStackTrace();
        }

        return Response;
    }

}
