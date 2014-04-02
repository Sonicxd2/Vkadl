package vkadl;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class VkDownloadThread {
    private VkAudio audio;
    private File outputLoc;

    public VkDownloadThread(VkAudio audio, File outputLoc) {
        super();
        this.audio = audio;
        this.outputLoc = outputLoc;
    }

    public void run() {
        File file = new File(outputLoc.getAbsolutePath() + File.separatorChar + audio.getFileName());
	    File fileTmp = new File(outputLoc.getAbsolutePath() + File.separatorChar + audio.getFileName() + ".vkadl");

	    if (file.exists()) {
		    System.out.println("Skipping " + audio.getFileName());
		    return;
	    }

	    if (fileTmp.exists()) {
		    fileTmp.delete();
	    }

        URL audioUrl = null;
        try {
            audioUrl = new URL(audio.getFileUrl());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

        System.out.println("Downloading:\t" + audio.getFileName());
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileTmp));
             BufferedInputStream in = new BufferedInputStream(audioUrl.openStream())) {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer, 0, buffer.length)) >= 0) {
                out.write(buffer, 0, read);
            }

	        fileTmp.renameTo(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("Done:\t" + audio.getFileName());
    }
}
