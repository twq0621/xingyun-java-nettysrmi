package com.ppsea;

import static org.jboss.netty.handler.codec.http.HttpResponseStatus.OK;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.io.UnsupportedEncodingException;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.DynamicChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;

/**
 * HTTP接收请求处理
 * 
 * @author sky
 * 
 */
public class HttpServerHandler extends SimpleChannelUpstreamHandler {

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent messageEvent) throws Exception {
		try {
			long startTime = System.currentTimeMillis();
			Channel channel = messageEvent.getChannel();
			
			System.out.println("start");
			HttpRequest request = (HttpRequest) messageEvent.getMessage();
			ChannelBuffer cb = request.getContent();
		
			
			cb.array(); 
			
			System.out.println("req info :" + new String(cb.array()));
			HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
			// 设置body数据
			ChannelBuffer buffer = new DynamicChannelBuffer(2048);
			try {
				buffer.writeBytes("cccc".getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			response.setContent(buffer);

			// 设置浏览器header
			response.setHeader("Content-Type", "text/vnd.wap.wml; charset=utf-8");
			response.setHeader("Content-Length", response.getContent().writerIndex());

			// Close the connection as soon as the error message is sent.
			channel.write(response).addListener(ChannelFutureListener.CLOSE);
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		Channel ch = e.getChannel();
		Throwable cause = e.getCause();
		
		ch.close();
	}

}
