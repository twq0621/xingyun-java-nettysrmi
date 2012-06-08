package com.ppsea.srmi.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.ppsea.srmi.Buffer;
import com.ppsea.srmi.InvokConnector;
import com.ppsea.srmi.SharedContext;

public class URLConnector implements InvokConnector {
	URL url;
	SharedContext context;
	
	public URLConnector(URL url, SharedContext context) {
		this.url = url;
		this.context = context;
	}

	@Override
	public Buffer request(Buffer request) {
		HttpURLConnection conn = null;
		InputStream is = null;
		Buffer result;
		try {
			conn = doRequest(request);
			is = conn.getInputStream();
			int size = conn.getContentLength();
		
			if(request.getData().length>=size){
				result = request;
				result.reset(0,size);
			}else{
				result = new Buffer(new byte[size], 0, size);
			}
			byte data[] = result.getData();
			int read = -1;
			int offset = 0;
			do{
				read=is.read(data, offset, data.length-offset);
				if(read<0){
					throw new IOException("错误的结尾");
				}
				offset+=read;
			}while(offset<size);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				is.close();
			}catch (Exception e) { }
			if(conn!=null)conn.disconnect();
		}
		return null;
	}
	
	HttpURLConnection doRequest(Buffer request) throws IOException{
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.connect();
		OutputStream out =null;
		try{
			out = conn.getOutputStream();
			request.writeToStream(out);
			out.flush();
		}finally{
			if(out!=null){
				out.close();
			}
		}
		return conn;
	}
	
	@Override
	public void requestNoReturn(Buffer request) {
		HttpURLConnection conn = null;
		try {
			conn = doRequest(request);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(conn!=null)conn.disconnect();
		}
	}
	
	
	
	
}
