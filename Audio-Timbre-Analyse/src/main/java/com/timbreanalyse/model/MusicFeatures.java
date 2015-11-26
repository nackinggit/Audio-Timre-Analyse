package com.timbreanalyse.model;

import jAudioFeatureExtractor.jAudioTools.FFT;

import java.util.Arrays;

import com.timbreanalyse.constant.Constant.InstrumentType;
import com.timbreanalyse.service.FeatureExtractorService;
import com.timbreanalyse.service.impl.FeatureExtractorServiceImpl;
import com.timbreanalyse.tools.AudioPreProcessTools;
import com.timbreanalyse.tools.MFCC;
import com.timbreanalyse.tools.MP3AudioAnalyser;

public class MusicFeatures {
	
	private long sampleRate;
	private double[] timeSequence;
	private double[] freqSequence;
	private double zeroCrossing;
	private double[] MFCCs;
	private double spectralCentroid;
	private double spectralFlux;
	private double[] featrues;
	private double[] freqEnergyRate;	//各个频段能量占比
	
	private InstrumentType type;
	
	FeatureExtractorService fService = new FeatureExtractorServiceImpl();
	
	double[][] frames; 
	public MusicFeatures(double[] timeSequence, long sampleRate) throws Exception {
		this.timeSequence = timeSequence;
		this.sampleRate = sampleRate;
		System.out.println("enframe");
		enframe();
		init();
		this.featrues = unionFeatrues();
		this.freqEnergyRate = calculateFreqRate();
	}
	
	/**
	 * 频段范围为
	 * 0-20Hz, 20-60Hz, 60-100Hz, 100-150Hz, 150-300Hz, 300-500Hz, 500-1000Hz, 1k-4kHz,
	 * 
	 * 4k-8kHz, 8K-16KHz, 16-20KHz
	 * @return
	 */
	private double[] calculateFreqRate() {
		if(this.freqSequence == null) return new double[0];
		double sum = 0;
		for(int i = 0; i < freqSequence.length; i++) {
			sum += freqSequence[i];
		}
		double[] result = new double[11];
		int size = (int)(sampleRate/freqSequence.length);
		int index = 0;
 		//0-20
		result[0] = MP3AudioAnalyser.freqEnergyRate(freqSequence, sum, index, index + 20/size);
 		//20-60
 		index += (20/size + 1);
 		result[1] = MP3AudioAnalyser.freqEnergyRate(freqSequence, sum, index, index + 40/size);
 		//60-100
 		index += (40/size + 1);
 		result[2] = MP3AudioAnalyser.freqEnergyRate(freqSequence, sum, index, index + 40/size);
 		//100-150
 		index += (40/size + 1);
 		result[3] = MP3AudioAnalyser.freqEnergyRate(freqSequence, sum, index, index + 50/size);
 		//150-300
 		index += (50/size + 1);
 		result[4] = MP3AudioAnalyser.freqEnergyRate(freqSequence, sum, index, index + 150/size);
 		//300-500
 		index += (150/size + 1);
 		result[5] = MP3AudioAnalyser.freqEnergyRate(freqSequence, sum, index, index + 200/size);
 		//500-1000
 		index += (200/size + 1);
 		result[6] = MP3AudioAnalyser.freqEnergyRate(freqSequence, sum, index, index + 500/size);
 		//1k-4k
 		index += (500/size + 1);
 		result[7] = MP3AudioAnalyser.freqEnergyRate(freqSequence, sum, index, index + 3000/size);
 		//4K-8K
 		index += (3000/size + 1);
 		result[8] = MP3AudioAnalyser.freqEnergyRate(freqSequence, sum, index, index + 4000/size);
 		//8k-16k
 		index += (4000/size + 1);
 		result[9] = MP3AudioAnalyser.freqEnergyRate(freqSequence, sum, index, index + 8000/size);
 		//16k-20k
 		index += (8000/size + 1);
 		result[10] = MP3AudioAnalyser.freqEnergyRate(freqSequence, sum, index, freqSequence.length - 1);
		return result;
	}
	private double[] unionFeatrues() {
		double[] res = new double[MFCCs.length + 3];
		double[] tmp = new double[3];
		tmp[0] = zeroCrossing;
		tmp[1] = spectralCentroid;
		tmp[2] = spectralFlux;
		for(int i = 0; i < res.length; i++) {
			if(i < MFCCs.length) {
				res[i] = MFCCs[i];
			} else {
				res[i] = tmp[i - MFCCs.length];
			}
		}
		return res;
	}
	private void enframe() {
		int len2nd = (int)(sampleRate * 250 / 1000);
		this.frames = AudioPreProcessTools.enframe(timeSequence, len2nd, len2nd/3);
	}
	private void init() throws Exception {
		System.out.println("init");
		calculateFreqAndSpectralFlux();
		System.out.println("过零率");
		this.zeroCrossing = fService.zeroCross(timeSequence);
		System.out.println("频谱质心");
		this.spectralCentroid = fService.spectrumCentor(freqSequence);
		System.out.println("MFCC");
		this.MFCCs = calculateMFCC();
	}
	
	private double[] calculateMFCC() {
		double[] result = new double[24];
		double[][] tmp = new double[frames.length][];
		int valid_size = jAudioFeatureExtractor.GeneralTools.Statistics.ensureIsPowerOfN(timeSequence.length, 2);
		MFCC mfcc = new MFCC(13, sampleRate, 24, valid_size, true, 22, true);
		for(int i = 0; i < frames.length; i++) {
			tmp[i] = mfcc.getParameters(frames[i]);
			System.out.println("第" + i + "帧：" + Arrays.toString(tmp[i]));
		}
		double[] d = AudioPreProcessTools.mean(tmp);
		for(int i = 0; i < result.length; i++) {
			if(i < d.length-1) {
				result[i] = d[i+1];
			} else {
				result[i] = d[i - d.length + 2] - d[i - d.length + 1];
			}
			
		}
		return result;
	}
	//假设250ms为一帧，即25ms的时间序列是稳定的,对每帧做STFT
	private void calculateFreqAndSpectralFlux() throws Exception {
		System.out.println("计算频谱和谱通量");
		for(int i = 0; i < frames.length; i++) {
			double[] subSequence = frames[i];
			FFT fft = new FFT(subSequence, null, false, true);
			frames[i] = fft.getMagnitudeSpectrum();
		}
		
		double[] result = new double[frames[0].length];
		double[] frameEnergy = new double[frames.length];
		
		for(int i = 0; i < frames.length; i++) {
			for(int j = 0; j < frames[i].length; j++) {
				frameEnergy[i] += frames[i][j];
			}
		}
		double subSum = 0;
		for(int i = 1; i < frameEnergy.length; i++) {
			subSum += frameEnergy[i] - frameEnergy[i-1];
		}
		this.spectralFlux = subSum/(frameEnergy.length - 1);
		
		for(int i = 0; i < frames[0].length; i++) {
			for(int j = 0; j < frames.length; j++ ) {
				result[i] += frames[j][i];
			}
		}
		this.freqSequence = result;
	}
	public double[] getTimeSequence() {
		return timeSequence;
	}
	
	public double[] getFreqSequence() {
		return freqSequence;
	}
	
	public double getZeroCrossing() {
		return zeroCrossing;
	}
	
	public double[] getMFCCs() {
		return MFCCs;
	}
	
	public double getSpectralCentroid() {
		return spectralCentroid;
	}
	
	public double getSpectralFlux() {
		return spectralFlux;
	}
	
	/**
	 * 特征参数向量，顺序为MFCCs + zeroCrossing + spectralCentroid + spectralFlux
	 * @return
	 */
	public double[] getFeatrues() {
		return featrues;
	}
	public long getSampleRate() {
		return sampleRate;
	}
	public void setSampleRate(long sampleRate) {
		this.sampleRate = sampleRate;
	}
	public InstrumentType getType() {
		return type;
	}
	public void setType(InstrumentType type) {
		this.type = type;
	}
	public double[] getFreqEnergyRate() {
		return freqEnergyRate;
	}
}
