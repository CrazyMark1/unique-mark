package com.mark.netty;

import com.mark.service.IdService;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/7 15:18
 * @QQ: 85104982
 */
public class IdServiceHandler extends ChannelInboundHandlerAdapter {
    private static final String GETID = "/getid";
    private static final String PARSE = "/parse";
    private IdService idService;


    public IdServiceHandler() {
        ApplicationContext context=new ClassPathXmlApplicationContext("META-INF/spring/provider.xml");
        idService= (IdService) context.getBean("idService");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest){
            HttpRequest r=(HttpRequest)msg;
            URI uri=new URI(r.uri());
            QueryStringDecoder decoder=new QueryStringDecoder(uri);
            Map<String,List<String>> map=decoder.parameters();
            if (uri.getPath().equalsIgnoreCase(GETID)){
                write(idService.getId()+"",HttpResponseStatus.OK,r,ctx);
            }else if (uri.getPath().equalsIgnoreCase(PARSE)){
                if (map.size()==0)
                    write(404+"",HttpResponseStatus.NOT_FOUND,r,ctx);
                else{
                    if (!map.get("id").get(0).equals("")){
                        write(idService.parseId(Long.parseLong(map.get("id").get(0))).toString(),HttpResponseStatus.OK,r,ctx);
                    }
                    else {
                        write(404+"",HttpResponseStatus.NOT_FOUND,r,ctx);
                    }
                }
            }
            else {
                write(404+"",HttpResponseStatus.NOT_FOUND,r,ctx);
            }

        }
    }

    private void write(String out, HttpResponseStatus status,HttpRequest r,ChannelHandlerContext ctx) throws UnsupportedEncodingException {
        FullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,
                Unpooled.wrappedBuffer(out.getBytes("UTF-8")));
        response.headers().set(CONTENT_TYPE,"text/json");
        response.headers().set(CONTENT_LENGTH,response.content().readableBytes());
        response.headers().set(EXPIRES,0);
        if(HttpHeaders.isKeepAlive(r)){
            response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }
        ctx.write(response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
