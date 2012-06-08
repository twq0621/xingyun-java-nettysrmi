package com.ppsea;

import static org.jboss.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.OK;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.DynamicChannelBuffer;
import org.jboss.netty.buffer.HeapChannelBufferFactory;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.frame.TooLongFrameException;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.jboss.netty.util.CharsetUtil;

import com.ppsea.srmi.Buffer;
import com.ppsea.srmi.NativeInvoker;


/**
 * HTTP接收请求处理
 * 
 * @author sky
 * 
 */
public class SrmiHandler extends SimpleChannelUpstreamHandler {

	NativeInvoker invoker;
	
	
	public SrmiHandler(NativeInvoker invoker) {
		super();
		this.invoker = invoker;
	}
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent messageEvent) throws Exception {
		try {
			Channel channel = messageEvent.getChannel();
			
			HttpRequest request = (HttpRequest) messageEvent.getMessage();
			ChannelBuffer cb = request.getContent();
			Buffer result = invoker.invoke(new Buffer(cb.array()));
			
			HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
			ChannelBuffer buffer = HeapChannelBufferFactory.getInstance().getBuffer(result.getData(), result.getOffset(), result.getLength());
			
			response.setContent(buffer);
			
			// 设置浏览器header
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
