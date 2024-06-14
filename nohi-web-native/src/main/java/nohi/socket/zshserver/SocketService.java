package nohi.socket.zshserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author NOHI
 * @date 2012-11-13
 */
public class SocketService extends Thread {
	private static SocketService socketService;
	private ServerSocket serverSocket = null;

	public static SocketService getInstance(int port)throws Exception {
		if (null == socketService) {
			socketService = new SocketService(port);
		}
		return socketService;
	}

	/**
	 * 单例
	 *
	 * @throws Exception
	 */
	private SocketService(int port) throws Exception {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("初始化ServerSocket失败");
		}
	}

	@Override
	public void run() {
		while (!this.isInterrupted()) {
			try {
				Socket socket = serverSocket.accept();
				if (null != socket) {
					new OperatorScoketdataThread(socket).start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void closeSocketServer() {
		try {
			if (null != serverSocket && !serverSocket.isClosed()) {
				serverSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
