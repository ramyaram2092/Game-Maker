package audio;

import java.io.File;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioFunctionality {

	private Media sound;
	MediaPlayer mediaPlayer;
	private String audioFile;
	
	public AudioFunctionality(String audioFile)
	{
		this.audioFile=audioFile;
		Media sound = new Media(new File(audioFile).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
	}
	
	public void playAudio()
	{
		mediaPlayer.play();
	}
}
