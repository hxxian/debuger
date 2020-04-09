package com.lightcone.debuger.listener;

import com.lightcone.commons.base.config.ServerConfig;
import com.lightcone.commons.base.config.ServerInit;
import com.lightcone.commons.base.util.EncryptUtil;
import com.lightcone.debuger.constant.ServerConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 服务器初始化
 *
 * Created by huang_xiao_xian
 * Date : 2019/3/26.
 */
@Component
public class ServerInitListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ServerInitListener.class);

    public static final String ENCRYPT_KEY = "s~to^ry@";

    @Value("${database.config.path}")
    private String path;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(contextRefreshedEvent.getApplicationContext().getParent() == null) {
            try {
                ServerConfig.getInstance().initXML(path);
                ServerInit.getInstance().init();
                ServerConfig.getInstance().initDBConfig();

                //设置加解密的key
                EncryptUtil.setKey(ENCRYPT_KEY);

                logger.info("服务器配置初始化成功@Host:" + ServerConstant.HOST_ADDRESS);
            } catch (Exception e) {
                logger.error("服务器配置初始化出错", e);
                throw new RuntimeException("ServerInitListener init error!", e);
            }
        }
    }
}
