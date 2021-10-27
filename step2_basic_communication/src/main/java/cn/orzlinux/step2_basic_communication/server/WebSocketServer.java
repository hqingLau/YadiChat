package cn.orzlinux.step2_basic_communication.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class WebSocketServer {
    private static WebSocketServer server;

    private static final int READ_IDLE_TIME_OUT = 0;
    private static final int WRITE_IDLE_TIME_OUT = 0;
    private static final int ALL_IDLE_TIME_OUT = 0;

    private WebSocketServer() {}

    // 只能通过getInstance获取单例
    public static WebSocketServer getInstance() {
        if(server==null) {
            server = new WebSocketServer();
        }
        return server;
    }

    public void run(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1); //只管接收
        EventLoopGroup workerGroup = new NioEventLoopGroup(); //默认cpu核数*2
        try {
            // 创建服务端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // netty的解码器和编码器
                            pipeline.addLast(new HttpServerCodec());
                            // 以块方式写，用于大数据分区传输
                            pipeline.addLast(new ChunkedWriteHandler());
                            // http在传输过程中分段，这里聚合多个段
                            pipeline.addLast(new HttpObjectAggregator(32*1024));
                            // wesocket数据压缩
                            pipeline.addLast(new WebSocketServerCompressionHandler());

                            //WebSocketServerProtocolHandler将http协议升级为ws协议，保持长连接
                            pipeline.addLast(new WebSocketServerProtocolHandler("/ws",null,true,10*1024));

                            // 当连接60s没有收到消息，就会触发idlestateevent事件,ChannelRead()方法未被调用
                            // 则触发一次MyTextWebSocketFrameHandler 的userEventTrigger()方法
                            // 心跳机制: 主要是用来检测远端是否存活，如果不存活或活跃则对空闲Socket连接进行处理避免资源的浪费
                            pipeline.addLast(new IdleStateHandler(READ_IDLE_TIME_OUT,WRITE_IDLE_TIME_OUT,ALL_IDLE_TIME_OUT, TimeUnit.SECONDS));

                            //自定义的handler ，处理业务逻辑
                            pipeline.addLast(new MyTextWebSocketFrameHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            SessionGroup.getInstance().shutdownGracefully();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
