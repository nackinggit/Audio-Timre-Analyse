package com.timbreanalyse.tools;

import jAudioFeatureExtractor.jAudioTools.AudioSamples;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;

/**
 * 
 * @author Administrator
 *
 */
public class MP3DataExtractor {
	public static final Logger logger = Logger.getLogger("DataExtractor");

	public static MP3AudioHeader audioHeaderExtractor(File file) {

		MP3AudioHeader audioHeader = null;
		MP3File mf = null;
		try {
			mf = new MP3File(file);
			audioHeader = mf.getMP3AudioHeader();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return audioHeader;
	}

	public static double[] audioValuesExtractor(File file) {
		double[] audioValues = null;
		try {
			final AudioInputStream in = AudioSystem
					.getAudioInputStream(new BufferedInputStream(
							new FileInputStream(file)));
			AudioInputStream din = null;
	        final AudioFormat baseFormat = in.getFormat();
	        final AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
	                baseFormat.getSampleRate(),
	                16,
	                baseFormat.getChannels(),
	                baseFormat.getChannels() * 2,
	                baseFormat.getSampleRate(),
	                false);

	        System.out.println("Channels : " + baseFormat.getChannels());                
	        din = AudioSystem.getAudioInputStream(decodedFormat, in);

	        AudioSamples audioSamples = new AudioSamples(din, "aaa", false);
	        audioValues = audioSamples.getSamplesMixedDown();
		} catch (FileNotFoundException e) {
			logger.info("MP3 file is not found.");
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			logger.info("file format is not supported.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return audioValues;
	}
}
