package nl.trojmans.realtime;

import java.io.IOException;
import java.net.InetAddress;
import java.util.TimeZone;

import org.bukkit.entity.Player;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;

public class RealTimeUser {
	
	private double latitude;
	private double longitude;
	private TimeZone tz;
	public RealTimeUser(DatabaseReader database, String ip, RealTimeConfig config){
		try{
			InetAddress ipAddress = InetAddress.getByName(ip);
			
			if (database == null) {
				tz = TimeZone.getTimeZone(timeZone.timeZoneByCountryAndRegion(null, null, config));
				return;
			}
			String country = database.city(ipAddress).getCountry().getIsoCode();
			String region = database.city(ipAddress).getMostSpecificSubdivision().getIsoCode();
			
			tz = TimeZone.getTimeZone(timeZone.timeZoneByCountryAndRegion(country, region, config));
						
		}catch(IOException ex){
			tz = TimeZone.getTimeZone(timeZone.timeZoneByCountryAndRegion(null, null, config));
			System.out.println("[RealTime] Something went wrong while looking for IP (" + ip + ") in the Database:\n" + ex.getMessage());
		}catch(GeoIp2Exception ex){
			tz = TimeZone.getTimeZone(timeZone.timeZoneByCountryAndRegion(null, null, config));
			System.out.println("[RealTime] " + ex.getMessage());
		}
	}
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the timeZone
	 */
	public TimeZone getTimeZone() {
		return tz;
	}
	/**
	 * @param timeZone the timeZone to set
	 */
	public void setTimeZone(TimeZone timeZone) {
		this.tz = timeZone;
	}
	/**
	 *@return the player
	 */
	 public Player getPlayer(){
		 return RealTime.getPlugin().getUserManager().getPlayer(this);
	 }
}
