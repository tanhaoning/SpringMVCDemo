package com.test.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by tanzepeng on 2016/11/29.
 */
public class RpcFramework {
    /**
     * 服务暴露
     */
    public static void export(final Object service, int port) throws Exception {
        if (null == service)
            throw new IllegalArgumentException("service is null.");

        if (port <= 0 || port > 65535)
            throw new IllegalArgumentException("Invalid port :" + port);

       // System.out.println("export service:" + service.getClass().getName() + " on port:" + port);

        ServerSocket server = new ServerSocket(port);

        for (; ; ) {
            try {
                final Socket socket = server.accept();
                System.out.println("Consumer response host:" + socket.getInetAddress() + ",port:" + socket.getPort() + "||localhost:" + socket.getLocalAddress() + ",localport:" + socket.getLocalPort());
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            try {
                                // 反序列化
                                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                                try {
                                    String methodName = input.readUTF();
                                    Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                                    Object[] arguments = (Object[]) input.readObject();
                                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                                    try {
                                        Method method = service.getClass().getMethod(methodName, parameterTypes);
                                        Object result = method.invoke(service, arguments);
                                        output.writeObject(result);
                                    } catch (Throwable t) {
                                        output.writeObject(t);
                                    } finally {
                                        output.close();
                                    }
                                } finally {
                                    input.close();
                                }
                            } finally {
                                socket.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 服务调用
     */
    public static <T> T refer(final Class<T> interfaceClass, final String host, final int port) throws Exception {
        if (null == interfaceClass)
            throw new IllegalArgumentException("interface is null.");

        if (!interfaceClass.isInterface())
            throw new IllegalArgumentException("The " + interfaceClass + " must be interface class.");

        if (null == host || host.length() == 0)
            throw new IllegalArgumentException("The host:" + host + " is null or ''");

        if (port <= 0 || port > 65535)
            throw new IllegalArgumentException("Invalid port :" + port);

        //System.out.println("Get remote service " + interfaceClass.getName() + " from server " + host + ":" + port);

        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
                Socket socket = new Socket(host, port);
                System.out.println("Provider request host:" + socket.getInetAddress() + ",port:" + socket.getPort() + "||localhost:" + socket.getLocalAddress() + ",localport:" + socket.getLocalPort());
                try {
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                    try {
                        output.writeUTF(method.getName());
                        output.writeObject(method.getParameterTypes());
                        output.writeObject(arguments);

                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                        try {
                            Object result = input.readObject();
                            if (result instanceof Throwable) {
                                throw (Throwable) result;
                            }
                            return result;
                        } finally {
                            input.close();
                        }
                    } finally {
                        output.close();
                    }
                } finally {
                    socket.close();
                }
            }
        });
    }
}
