package proj.synteam.easybtc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.Message;
import android.util.Log;

public class TCPThread extends Thread {

	private boolean kill;
	private boolean writable;
	private String ipAddress = null;
	private int portNo = -1;
	private String recvMsg;
	private String sendMsg;

	public TCPThread(String ipAddress, int portNo) {
		this.ipAddress = ipAddress;
		this.portNo = portNo;
	}

	@Override
	public void run() {
		Socket socket = connectToServer(ipAddress, portNo);
		if (null == socket) {
			return;
		}
		while (!kill) {
			recvMsg = read(socket);
			if (recvMsg == null) {

			} else {
				Log.d(Service_WifiScanner.TAG, "RECEIVED MESSAGE : " + recvMsg);
			}
			if (writable) {
				Log.d(Service_WifiScanner.TAG, "∫∏≥ø");
				write(socket, sendMsg);
				sendMsg = null;
				writable = false;
			}
		}
		Log.d(Service_WifiScanner.TAG, "¥›¿ª∞≈¿”");
		disconnect(socket);
		Log.d(Service_WifiScanner.TAG, "¥›¿Ω");
	}

	private Socket connectToServer(String ipAddress, int portNo) {
		Socket socket = null;
		try {
			socket = new Socket(ipAddress, portNo);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return socket;
	}

	private String read(Socket socket) {
		String textFromServer = null;
		DataInputStream textMsgSteamFromServer = null;

		try {
			textMsgSteamFromServer = new DataInputStream(
					socket.getInputStream());
			textFromServer = textMsgSteamFromServer.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
			textFromServer = null;
		} finally {
			closeInputStream(textMsgSteamFromServer);
		}

		return textFromServer;
	}

	private void write(Socket socket, String message) {
		DataOutputStream dos = null;

		try {
			dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(message);
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeOutputStream(dos);
		}

	}

	private void closeInputStream(InputStream is) {
		try {
			if (null != is) {
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void closeOutputStream(OutputStream os) {
		try {
			if (null != os) {
				os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void disconnect(Socket socket) {
		try {
			if (null != socket) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	synchronized void setWritable() {
		writable = true;
	}

	synchronized void setSendMsg(String str) {
		sendMsg = str;
	}

}
