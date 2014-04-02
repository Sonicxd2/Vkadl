package vkadl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class VkAudioGetRequest {
	private String accessToken;
	private String ownerId;

	public VkAudioGetRequest(String accessToken, String ownerId) {
		this.accessToken = accessToken;
		this.ownerId = ownerId;
	}

	public List<VkAudio> get(int offset) {
		List<VkAudio> audios = new ArrayList<>();

		try {
			URL vkAudioGet = new URL("https://api.vk.com/method/audio.get?owner_id=" + ownerId +
					"&count=6000&access_token=" + accessToken +
					"&offset=" + offset);
			URLConnection urlCon = vkAudioGet.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
			String inLine;
			StringBuilder data = new StringBuilder();

			while ((inLine = in.readLine()) != null) {
				data.append(inLine);
			}

			System.out.println(data);

			JsonArray items = new JsonParser().parse(data.toString()).getAsJsonObject().getAsJsonArray("response");
			for (int i = 2; i < items.size() - 1; i++) {
				JsonObject audioObj = items.get(i).getAsJsonObject();
				VkAudio vkAudio = new VkAudio();
				vkAudio.setArtist(audioObj.get("artist").getAsString());
				vkAudio.setTitle(audioObj.get("title").getAsString());
				vkAudio.setFileUrl(audioObj.get("url").getAsString());
				audios.add(vkAudio);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return audios;
	}
}
