package vkadl;

import java.io.File;

public class VkAudio {
    private String artist;
    private String title;
    private String fileUrl;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return (getArtist().trim() + " - " + getTitle().trim() + ".mp3").replace(File.separatorChar, '_');
    }
}
