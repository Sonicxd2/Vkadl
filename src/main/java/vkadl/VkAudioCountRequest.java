package vkadl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class VkAudioCountRequest {
    private String ownerId;
	private String accessToken;

    public VkAudioCountRequest(String accessToken, String ownerId) {
        this.ownerId = ownerId;
	    this.accessToken = accessToken;
    }

    public int get() {
        try {
	        URL vkAudioCount = new URL("https://api.vk.com/method/audio.getCount?owner_id="+ownerId+"&access_token="+accessToken);
	        URLConnection urlCon = vkAudioCount.openConnection();
	        BufferedReader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
	        String inLine;
	        StringBuilder data = new StringBuilder();

	        while ((inLine = in.readLine()) != null) {
		        data.append(inLine);
	        }

	        JsonObject response = new JsonParser().parse(data.toString()).getAsJsonObject();
			return response.get("response").getAsInt();
        } catch (IOException ex) {
	        ex.printStackTrace();
	        return 0;
        }
    }
}
