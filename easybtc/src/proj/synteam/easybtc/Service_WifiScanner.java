package proj.synteam.easybtc;

import java.util.List;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;

public class Service_WifiScanner extends Service {
	public static final String TAG = "Service_WifiScanner"; // tag for debug
	public static final String ERR = "Error";
	public static final String AP_NAME = "lenski-labs";
	public static final String AP_PASSWORD = "12345678";
	public static final String IP = "192.168.0.5";
	public static final int PORT = 3490;
	// public static final String AP_NAME = "Frontier(2.4GHz)";
	// public static final String AP_PASSWORD = "showmethemoney";
	// public static final String AP_NAME = "A208_Seol";
	// public static final String AP_PASSWORD = "9876543210";
	public static final String[] SECURITY_MODES = { "WEP", "WPA", "WPA2",
			"WPA_EAP", "IEEE8021X" };
	private WifiManager wifiManager;
	private List<ScanResult> scanResult;
	private NotificationManager mNotificationManager;
	private boolean isDetected;
	private int netId;
	private TCPThread tcpThread;

	public void onCreate() {
		super.onCreate();
		init();
		scanEasybtc(false);
		connectEasybtc();
		sendInfo();
		terminate();

	}

	private void sendInfo() {
		while (!isConnected()) {

		}
		
		log("LOCAL IP : " + Utils.getIpAddress());
		JSON json = new JSON();
		json.put("name", "heojun");
		json.put("age", "26");
		tcpThread.start();
		tcpThread.setSendMsg(json.toJSONString());
		tcpThread.setWritable();
		log(json.toJSONString());

	}

	private boolean isConnected() {
		if (Utils.getIpAddress().contains(IP.substring(0, 4))) {
			return true;
		} else {
			return false;
		}
	}

	
	private void init() {
		wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		tcpThread = new TCPThread(IP, PORT);
	}


	private void scanEasybtc(final boolean notification) {
		while (!isDetected) {
			scanResult = wifiManager.getScanResults();
			for (ScanResult result : scanResult) {
				if (result.SSID.equals(AP_NAME)) {
					log(result.SSID + " is covered!");
					if (notification) {
						Utils.makeNotification(mNotificationManager,
								result.SSID + " is covered!");
					}
					isDetected = true;
				}
			}

			try {
				log("looking for AP");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void connectEasybtc() {
		log("Connecting...");
		if (wifiManager.getConnectionInfo().getSSID()
				.equals("\"" + AP_NAME + "\"")) {
			log("It is already connected");
		} else {
			for (int i = 0; i < scanResult.size(); i++) {
				ScanResult availableAP = scanResult.get(i);
				if (availableAP.SSID.equals(AP_NAME)) {
					log("AP NAME : " + availableAP.SSID);
					String securityMode = getScanResultSecurity(availableAP);
					log("SECURITY MODE : " + securityMode);

					WifiConfiguration wc = new WifiConfiguration();
					wc.SSID = "\"" + AP_NAME + "\"";
					wc.status = WifiConfiguration.Status.DISABLED;

					if (securityMode.equalsIgnoreCase("OPEN")) {
						wc.allowedKeyManagement
								.set(WifiConfiguration.KeyMgmt.NONE);
						wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
						wc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
						wc.allowedAuthAlgorithms.clear();
						wc.allowedPairwiseCiphers
								.set(WifiConfiguration.PairwiseCipher.CCMP);
						wc.allowedGroupCiphers
								.set(WifiConfiguration.GroupCipher.WEP40);
						wc.allowedGroupCiphers
								.set(WifiConfiguration.GroupCipher.WEP104);
						wc.allowedGroupCiphers
								.set(WifiConfiguration.GroupCipher.CCMP);
						wc.allowedGroupCiphers
								.set(WifiConfiguration.GroupCipher.TKIP);
						log("Open AP");
					} else if (securityMode.equalsIgnoreCase("WEP")) {
						wc.allowedKeyManagement
								.set(WifiConfiguration.KeyMgmt.NONE);
						wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
						wc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
						wc.allowedAuthAlgorithms
								.set(WifiConfiguration.AuthAlgorithm.OPEN);
						wc.allowedAuthAlgorithms
								.set(WifiConfiguration.AuthAlgorithm.SHARED);
						wc.allowedPairwiseCiphers
								.set(WifiConfiguration.PairwiseCipher.CCMP);
						wc.allowedGroupCiphers
								.set(WifiConfiguration.GroupCipher.WEP40);
						wc.allowedGroupCiphers
								.set(WifiConfiguration.GroupCipher.WEP104);
						wc.wepKeys[0] = "\"" + AP_PASSWORD + "\"";
						wc.wepTxKeyIndex = 0;
					} else {
						wc.preSharedKey = "\"" + AP_PASSWORD + "\"";
						wc.allowedAuthAlgorithms
								.set(WifiConfiguration.AuthAlgorithm.OPEN);
						wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
						wc.allowedKeyManagement
								.set(WifiConfiguration.KeyMgmt.WPA_PSK);
						wc.allowedPairwiseCiphers
								.set(WifiConfiguration.PairwiseCipher.CCMP);
						wc.allowedPairwiseCiphers
								.set(WifiConfiguration.PairwiseCipher.TKIP);
						wc.allowedGroupCiphers
								.set(WifiConfiguration.GroupCipher.CCMP);
						wc.allowedGroupCiphers
								.set(WifiConfiguration.GroupCipher.TKIP);
					}

					wifiManager.setWifiEnabled(true);

					List<WifiConfiguration> configs = wifiManager
							.getConfiguredNetworks();
					boolean isExist = false;

					for (int j = 0; j < configs.size(); j++) {
						WifiConfiguration configured = configs.get(j);
						if (configured.SSID.equals(wc.SSID)) {
							isExist = true;
							netId = configured.networkId;
							log("already exist " + netId);
							break;
						}
					}
					if (isExist) {

					} else {
						netId = wifiManager.addNetwork(wc);
						log(netId + "");
					}

					wifiManager.disconnect();
					wifiManager.enableNetwork(netId, true);
					wifiManager.reconnect();

				}
			}

		}

	}

	private String getScanResultSecurity(ScanResult scanResult) {
		final String capability = scanResult.capabilities;
		for (int i = 0; i < SECURITY_MODES.length; i++) {
			if (capability.contains(SECURITY_MODES[i])) {
				return SECURITY_MODES[i];
			}
		}
		return "Open";
	}

	private void terminate() {
		Activity_Main.shut = true;
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	private void log(String message) {
		Log.i(TAG, message);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
