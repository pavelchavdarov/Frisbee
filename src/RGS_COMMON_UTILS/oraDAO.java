package RGS_COMMON_UTILS;

import oracle.jdbc.OracleDriver;
import sun.nio.ch.ChannelInputStream;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.sql.Blob;
import java.sql.Clob;

/**
 * Created by p.chavdarov on 20/01/2017.
 */
public class oraDAO {
    public static Clob CreateClobString(String source) throws Exception{
        //resStr= RGS_COMMON_UTILS.String4CFT.setPar(resStr,"state: ", source);
        //source = String.format("%-1000s", source);

        char[] cbuf = source.toCharArray();
        System.err.println("creating clob...");
        System.err.println("source: " + source);
        oracle.jdbc.OracleConnection oraConn =
//                (oracle.jdbc.OracleConnection)DriverManager.getConnection("jdbc:oracle:thin:@test03.msk.russb.org:1521:rbotest2","ibs","12ibs");
                (oracle.jdbc.OracleConnection)new OracleDriver().defaultConnection();

        Clob result =  oracle.sql.CLOB.createTemporary(oraConn, true, oracle.sql.CLOB.DURATION_SESSION);

        Writer wr = result.setCharacterStream(1);
        wr.write(cbuf);
        wr.flush();
        return result;
    }

    public static Clob CreateClobStream(InputStream inStream) throws Exception{
        char[] cbuff = new char[1024];
        System.err.println("creating clob from InputStream...");
        oracle.jdbc.OracleConnection oraConn =
                (oracle.jdbc.OracleConnection)new OracleDriver().defaultConnection();

        Clob result =  oracle.sql.CLOB.createTemporary(oraConn, true, oracle.sql.CLOB.DURATION_SESSION);

        Writer wr = result.setCharacterStream(1);
        Reader rd = Channels.newReader(Channels.newChannel(inStream),"UTF-8");
        int len = 0;
        while((len = rd.read(cbuff)) != -1)
            wr.write(cbuff,0,len);
        wr.flush();
        return result;
    }

    public static Blob CreateBlob(String source) throws Exception{
        //resStr= RGS_COMMON_UTILS.String4CFT.setPar(resStr,"state: ", source);
        //source = String.format("%-1000s", source);
        System.err.println("creating blob...");
        System.err.println("source: " + source);
        oracle.jdbc.OracleConnection oraConn =
//                (oracle.jdbc.OracleConnection)DriverManager.getConnection("jdbc:oracle:thin:@test03.msk.russb.org:1521:rbotest2","ibs","12ibs");
                (oracle.jdbc.OracleConnection)new OracleDriver().defaultConnection();

        Blob result = oracle.sql.BLOB.createTemporary(oraConn, true, oracle.sql.BLOB.DURATION_SESSION);
        OutputStream outStream = result.setBinaryStream(1);
        byte[] buf = source.getBytes();
        outStream.write(buf);
        outStream.flush();
        return result;
    }
}
