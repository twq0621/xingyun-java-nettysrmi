package com.ppsea;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



/**
 * Hello world!
 *
 */
public class TestHttp 
{
    public static void main( String[] args ) throws MalformedURLException
    {
        
        URL url = new URL("http://127.0.0.1:8090");
        try {
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.connect();
			
			System.out.println("send...");
			OutputStream out = conn.getOutputStream();
			byte[] buf = "aaa".getBytes();
			out.write(buf);
			out.close();
			
			System.out.println("recv...");
			InputStream in = null;
			in = conn.getInputStream();
			int length = in.available();
			buf = new byte[length];
			in.read(buf);
			in.close();
			System.out.print(new String(buf));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }
}
