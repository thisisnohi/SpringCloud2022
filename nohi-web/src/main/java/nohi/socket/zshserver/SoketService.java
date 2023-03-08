package nohi.socket.zshserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author NOHI
 * @date 2012-11-13
 */
public class SoketService extends Thread {
	private static SoketService soketService;
	private ServerSocket serverScoket = null;

	public static  SoketService getInstanse(int port)throws Exception {
		if (null == soketService) {
			soketService = new SoketService(port);
		}
		return soketService;
	}

	/**
	 * 单例
	 *
	 * @throws Exception
	 */
	private SoketService(int port) throws Exception {
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
					new OperatorScoketdataThread(socket).start();
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
