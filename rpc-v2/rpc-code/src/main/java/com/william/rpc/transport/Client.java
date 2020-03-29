package com.william.rpc.transport;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    private String host;
    private int port;

    private Socket socket;

    private OutputStream outputStream;

    private InputStream inputStream;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }


}
