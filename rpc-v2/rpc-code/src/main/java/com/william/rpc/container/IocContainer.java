package com.william.rpc.container;

import java.util.concurrent.ConcurrentHashMap;

public class IocContainer {
    public static ConcurrentHashMap<String,Class> ioc = new ConcurrentHashMap<>();

}
