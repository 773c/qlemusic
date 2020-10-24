package com.cjj.qlemusic.common.constant;

/**
 * 消息队列常量
 */
public enum  QueueConstant {
    /**
     * 消息通知队列
     */
    QUEUE_ORDER_CANCEL("qlemusic.msg.direct", "qlemusic.msg.cancel", "qlemusic.msg.cancel"),
    /**
     * 消息通知ttl队列
     */
    QUEUE_TTL_ORDER_CANCEL("qlemusic.msg.direct.ttl", "qlemusic.msg.cancel.ttl", "qlemusic.msg.cancel.ttl");

    /**
     * 交换名称
     */
    private String exchange;
    /**
     * 队列名称
     */
    private String name;
    /**
     * 路由键
     */
    private String routeKey;

    public String getExchange() {
        return exchange;
    }

    public String getName() {
        return name;
    }

    public String getRouteKey() {
        return routeKey;
    }

    QueueConstant(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }
}
