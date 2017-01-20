package RGS_FRISBEE;

import RGS_COMMON_UTILS.ConnectionInterface;
import RGS_COMMON_UTILS.String4CFT;
import static RGS_COMMON_UTILS.oraDAO.CreateClob;

import java.io.BufferedReader;
import java.sql.Clob;




/**
 * Created by p.chavdarov on 20/01/2017.
 */
public class FrisbeeAPI {

    private static ConnectionInterface FBconn;

//    private static Clob CreateClob(String source) throws Exception{
//        //resStr= String4CFT.setPar(resStr,"state: ", source);
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

    public static Clob SendRequest(Clob request) throws Exception {
        Clob clobResponse = null;
        char[] cbuf = new char[1];
        String strRequest = "";
        String strResponse = "";
        try {
            FBconn = new FBConnection();

            BufferedReader br = (BufferedReader) request.getCharacterStream();
            while(br.read(cbuf) != -1){
                strRequest.concat(String.valueOf(cbuf));
            }
            br.close();
            FBconn.sendData(strRequest);
            strResponse = FBconn.getData();
            clobResponse = CreateClob(strResponse);
        }catch(Exception e){
            strResponse = String4CFT.setPar(strResponse,"error", e.getMessage());
            if (strResponse.length()>=1000)
                strResponse = strResponse.substring(0, 1000);
            else
                strResponse = String.format("%-1000s", strResponse);

            clobResponse = CreateClob(strResponse);
        }

        return clobResponse;
    }

    public static String SendRequest(String request) throws Exception {
        String Response = "";
//        char[] cbuf = new char[1];
//        String strRequest = "";
//        String strResponse = "";
        try {
            FBconn = new FBConnection();

            FBconn.sendData(request);
            Response = FBconn.getData();
        }catch(Exception e){
            Response = String4CFT.setPar(Response,"error", e.getMessage());
            if (Response.length()>=1000)
                Response = Response.substring(0, 1000);
            else
                Response = String.format("%-1000s", Response);
        }

        return Response;
    }

}
