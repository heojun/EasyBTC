package proj.synteam.easybtc;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Utils {
	public static void makeNotification(NotificationManager mNotificationManager, String text){
		
		Notification.Builder builder = new Notification.Builder(Activity_Main.context_Main);
		builder.setSmallIcon(R.drawable.abc_btn_check_material).setTicker("EasyBtc").setWhen(System.currentTimeMillis());
		Notification notification = builder.getNotification();

		CharSequence contentTitle = "EasyBtc";
		CharSequence contentText = text;
		Intent notificationIntent = new Intent(Activity_Main.context_Main, Activity_Main.class);
		PendingIntent contentIntent = PendingIntent.getActivity(Activity_Main.context_Main, 0, notificationIntent, 0);
		notification.setLatestEventInfo(Activity_Main.context_Main, contentTitle, contentText, contentIntent);
		mNotificationManager.notify(1, notification);
	}
	
	public static String getIpAddress() {
		try {
			for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en
					.hasMoreElements();) {
				NetworkInterface intf = (NetworkInterface) en.nextElement();
				for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr
						.hasMoreElements();) {
					InetAddress inetAddress = (InetAddress) enumIpAddr
							.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& inetAddress instanceof Inet4Address) {
						String ipAddress = inetAddress.getHostAddress()
								.toString();
						Log.e("IP address", "" + ipAddress);
						return ipAddress;
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("Socket exception in GetIP Address of Utilities",
					ex.toString());
		}
		return null;
	}
}
