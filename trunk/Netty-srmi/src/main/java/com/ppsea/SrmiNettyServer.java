package com.ppsea;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;

import com.ppsea.srmi.NativeInvoker;
import com.ppsea.srmi.SharedContext;
import com.ppsea.srmi.serialization.BinarySerializer;


public class SrmiNettyServer {
	String host = "127.0.0.1";
	int port = 8090;
	
	SrmiHandler handler;
	NativeInvoker invoker;
	SharedContext context;
	
	
	public SrmiNettyServer(SharedContext context,String host,int port) {
		super();
		this.context = context;
		this.host = host;
		this.port = port;
		invoker = new NativeInvoker(context,new BinarySerializer());
		handler = new SrmiHandler(invoker);
	}
	
	public NativeInvoker getInvoker(){
		return invoker;
	}
	
	public SrmiHandler getHandler(){
		return handler;
	}

	public void startup(){
		// 配置服务器-使用java线程池作为解释线程
		ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

		// 设置 pipeline factory.
		bootstrap.setPipelineFactory(new HttpServerPipelineFactory(handler));
		// bootstrap.setOption("network.tcp.blocking", true);

		// bootstrap.setOption("tcpNoDelay", true);
		// bootstrap.setOption("keepAlive", true);

		// 绑定端口
		bootstrap.bind(new InetSocketAddress(host, port));
		System.out.print("start HttpServer host:" + host + ",port:" + port);
	}
	
	private static class HttpServerPipelineFactory implements ChannelPipelineFactory {
		private SimpleChannelUpstreamHandler handler = null;

		public HttpServerPipelineFactory(SimpleChannelUpstreamHandler handler) {
			this.handler = handler;
		}

		public ChannelPipeline getPipeline() throws Exception {
			// Create a default pipeline implementation.
			ChannelPipeline pipeline = Channels.pipeline();
			pipeline.addLast("decoder", new HttpRequestDecoder());
			pipeline.addLast("encoder", new HttpResponseEncoder());
			// pipeline.addLast("pipelineExecutor", new ExecutionHandler(new
			// OrderedMemoryAwareThreadPoolExecutor(1000, 0, 0)));
			// http处理handler
			pipeline.addLast("handler", handler);
			return pipeline;
		}
	}	
}
