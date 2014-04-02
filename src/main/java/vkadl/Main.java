package vkadl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
	    try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
		    System.out.println("Please go to this URL and authenticate:");
		    System.out.println("https://oauth.vk.com/authorize?client_id=4253657%20&scope=8&redirect_uri=https://oauth.vk.com/blank.html&display=mobile&v=5.16&response_type=token"); // uhoh
		    System.out.println("Take the access token from your browser's address bar and put it here:");
		    System.out.print("Access token: ");
		    String accessToken = in.readLine();

		    System.out.print("Owner ID: ");
		    String ownerId = in.readLine();

		    System.out.print("#Threads: ");
		    int numThreads = 10;

		    try {
			    numThreads = Integer.parseInt(in.readLine());
			    if (numThreads <= 0) {
				    System.out.println("Invalid thread amount! Defaulting to 10.");
			    } else {

			    }
		    } catch (NumberFormatException ex) {
			    System.out.println("Invalid thread amount! Defaulting to 10.");
		    }

		    System.out.print("Output location: ");
		    File outputLoc = new File(in.readLine());

		    System.out.println("All set and ready to go!");
		    Vkadl vkadl = new Vkadl(accessToken, ownerId, numThreads, outputLoc);
		    vkadl.start();
	    } catch (IOException ex) {
		    ex.printStackTrace();
	    }
    }
}
