package de.vincidev.bungeeban.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import de.vincidev.bungeeban.Main;

public class UpdateChecker {
	public static boolean updateAvailable(Main instance, String rsID) {
		int localVersion = Integer.parseInt(instance.getDescription().getVersion().replace(".", ""));
		try {
			HttpURLConnection con = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php")
					.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.getOutputStream()
					.write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=" + rsID)
							.getBytes("UTF-8"));
			String content = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
			int onlineVersion = Integer.parseInt(content.replace(".", ""));
			if (onlineVersion > localVersion) {
				return true;
			} else {
				return false;
			}
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return false;
	}
}
