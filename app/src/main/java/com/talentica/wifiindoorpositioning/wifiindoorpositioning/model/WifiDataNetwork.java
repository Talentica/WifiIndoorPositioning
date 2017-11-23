package com.talentica.wifiindoorpositioning.wifiindoorpositioning.model;

import android.net.wifi.ScanResult;
import android.os.Parcel;
import android.os.Parcelable;

public class WifiDataNetwork implements Comparable<WifiDataNetwork>, Parcelable {
	private String bssid;
	private String ssid;
	private String capabilities;
	private int frequency;
	private int level;
	private long timestamp;

	public WifiDataNetwork(ScanResult result) {
		bssid = result.BSSID;
		ssid = result.SSID;
		capabilities = result.capabilities;
		frequency = result.frequency;
		level = result.level;
		timestamp = System.currentTimeMillis();
	}

	public WifiDataNetwork(Parcel in) {
		bssid = in.readString();
		ssid = in.readString();
		capabilities = in.readString();
		frequency = in.readInt();
		level = in.readInt();
		timestamp = in.readLong();
	}

	public static final Creator<WifiDataNetwork> CREATOR = new Creator<WifiDataNetwork>() {
		public WifiDataNetwork createFromParcel(Parcel in) {
			return new WifiDataNetwork(in);
		}

		public WifiDataNetwork[] newArray(int size) {
			return new WifiDataNetwork[size];
		}
	};

	/**
	 * Converts a WiFi frequency to the corresponding channel.
	 * 
	 * @param freq
	 *            frequency as given by
	 *            {@link ScanResult frequency}
	 * @return the channel associated with the given frequency
	 */
	public static int convertFrequencyToChannel(int freq) {
		if (freq >= 2412 && freq <= 2484) {
			return (freq - 2412) / 5 + 1;
		} else if (freq >= 5170 && freq <= 5825) {
			return (freq - 5170) / 5 + 34;
		} else {
			return -1;
		}
	}

	public String getBssid() {
		return bssid;
	}

	public void setBssid(String bssid) {
		this.bssid = bssid;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(String capabilities) {
		this.capabilities = capabilities;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int compareTo(WifiDataNetwork another) {
		return another.level - this.level;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(bssid);
		dest.writeString(ssid);
		dest.writeString(capabilities);
		dest.writeInt(frequency);
		dest.writeInt(level);
		dest.writeLong(timestamp);
	}

	@Override
	public String toString() {
		return ssid + " addr:" + bssid + " lev:" + level + "dBm freq:" + frequency + "MHz cap:" + capabilities;
	}
}
