package com.timbreanalyse.tools;

public class MP3AudioAnalyser {
	
	public static double freqEnergyRate(double[] freqEnergy,double sum, int from, int to) {
		double subSum = 0;
		for(int i = from; i < to; i++) {
			subSum += freqEnergy[i];
		}
		return subSum/sum;
	}

	public static double[] freqSequence(double[] audioValue) throws Exception {
		jAudioFeatureExtractor.jAudioTools.FFT fft = new jAudioFeatureExtractor.jAudioTools.FFT(
				audioValue, null, false, true);

		double[] magnitudeValues = fft.getMagnitudeSpectrum();

		return magnitudeValues;
	}
}
