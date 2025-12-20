package nohi.socket.njserver;

import nohi.socket.zshserver.OperatorScoketdataThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author NOHI
 * 2021-10-11 19:19
 **/
public class NjSocketService extends Thread {
    private static NjSocketService soketService;
    private ServerSocket serverScoket = null;

    public static NjSocketService getInstanse(int port) throws Exception {
        if (null == soketService) {
            soketService = new NjSocketService(port);
        }
        return soketService;
    }

    /**
     * 单例
     *
     * @throws Exception
     */
    private NjSocketService(int port) throws Exception {
        try {
            serverScoket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("初始化ServerSocket失败");
        }
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                Socket socket = serverScoket.accept();
                if (null != socket) {
                    new NjOperatorScoketdataThread(socket).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeSocketServer() {
        try {
            if (null != serverScoket && !serverScoket.isClosed()) {
                serverScoket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
