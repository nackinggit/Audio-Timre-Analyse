package test;

import java.io.File;

import org.jaudiotagger.audio.mp3.MP3File;

import com.timbreanalyse.model.MusicFeatures;
import com.timbreanalyse.tools.MP3DataExtractor;

public class TestAudio {
	
	public static String s = "G:\\to be finish\\测试\\乐器分类数据库\\";
	public static String audioFile = "笛子_花好月圆1.mp3";
	
	public static void main(String[] args) throws Exception {
		File file = new File(s + audioFile);
		MP3File mp3File = new MP3File(file);
		long sampleRate = mp3File.getAudioHeader().getSampleRateAsNumber();
		double[] timeSequence = MP3DataExtractor.audioValuesExtractor(file);
		MusicFeatures musicFeatures = new MusicFeatures(timeSequence, sampleRate);
		System.out.println(musicFeatures);
	}
	
}
