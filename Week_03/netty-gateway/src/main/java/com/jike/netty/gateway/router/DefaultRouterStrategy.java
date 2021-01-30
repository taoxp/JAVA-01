package com.jike.netty.gateway.router;

import java.util.List;
import java.util.Random;

/**
 * @author: taoxp
 * @create: 2021-01-30 16:44
 */
public class DefaultRouterStrategy implements RouterStrategy {

    private List<String> urls;
    private Random random=null;

    public DefaultRouterStrategy(List<String> urls) {
        this.urls = urls;
        random=new Random();
    }


    @Override
    public String router() {
        return urls.get(random.nextInt(urls.size()));
    }
}
