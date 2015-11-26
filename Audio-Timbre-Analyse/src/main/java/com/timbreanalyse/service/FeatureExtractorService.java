package com.timbreanalyse.service;

public interface FeatureExtractorService {
	/**
	 * 计算MFCC系数
	 * @param audioValues_FFT
	 * @return
	 */
	public double[] MFCCs(double[] audioValues, double sampleRate);
	
	/**
	 * 频谱质心
	 * @param audioValues_FFT
	 * @return
	 */
	public double spectrumCentor(double[] audioMagnitudeValues);
	
	/**
	 * 过零率
	 * @param audioValues_FFT
	 * @return
	 */
	public double zeroCross(double[] audioValues);
	
}
