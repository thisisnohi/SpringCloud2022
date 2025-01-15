package nohi.socket.zshserver;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

/**
 * 处理本地Socket服务线程
 *
 * @author NOHI
 * @date 2012-11-13
 */
@Slf4j
public class OperatorScoketdataThread extends Thread {
	private Socket socket;
	public static final int MSG_LENGTH = 4;

	public OperatorScoketdataThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			// 将输入的字节流转化为高层流
			String temp = null;

			byte[] b = new byte[1024 * 10];
			int i = socket.getInputStream().read(b);
			temp = new String(b,0,i);
			log.debug("服务器接收数据 [" + temp + "]");

			String response = null;
			if (StringUtils.isBlank(temp) || temp.length() < MSG_LENGTH) {
				response = "";
			}

			// 处理响应
			response = "this is reback";
			log.debug("服务器端返回数据[" + response + "]");

			//　返回
			BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
			bos.write(response.getBytes());
			bos.flush();
			bos.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
