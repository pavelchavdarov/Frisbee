package RGS_COMMON_UTILS;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;

/**
 *
 * @author p.chavdarov
 */
public interface ConnectionInterface {

  public String GET_Request(String uri, Object... objects) throws IOException;
  public Blob GET_RequestDB(String uri, Object... objects);
  public String POST_Request(String uri, Object... objects) throws IOException;
  public Blob POST_RequestDBBlob(String uri, String contentType, Object... objects) throws IOException, SQLException;
  public Blob POST_RequestDBBlob(String uri, Object... objects) throws IOException, SQLException;
  public Clob POST_RequestDBClob(String uri, String contentType, Object... objects) throws IOException, SQLException;
  public Clob POST_RequestDBClob(String uri, Object... objects) throws IOException, SQLException;
  public String PUT_Request(String uri, String contentType, Object... objects);
  public Blob PUT_RequestDB(String uri, String contentType, Object... objects);

  public void initConnection();
  public void closeConnection() throws IOException;

  public void setProxy(String url, int port, String schema);
  public void setTarget(String url, int port, String schema);
  public HttpHost getTarget();

//  public void sendData(String pData) throws Exception;
//  public String getData() throws Exception;
//  public void sendFile(Blob pBlob) throws Exception;
//  public Blob getFile() throws Exception;
}