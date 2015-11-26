package com.timbreanalyse.service.impl;

import com.timbreanalyse.service.FeatureExtractorService;

public class FeatureExtractorServiceImpl implements FeatureExtractorService {
	public double spectrumCentor(double[] audioMagnitudeValues) {

		double total = 0.0;
		double weighted_total = 0.0;
		for (int bin = 0; bin < audioMagnitudeValues.length; bin++)
		{
			weighted_total += bin * audioMagnitudeValues[bin];
			total += audioMagnitudeValues[bin];
		}

		double result = 0;
		if(total != 0.0){
			result = weighted_total / total;
		}else{
			result = 0.0;
		}
		return result;
	}

	public double zeroCross(double[] audioValues) {
		long count = 0;
		for (int samp = 0; samp < audioValues.length - 1; samp++)
		{
			if (audioValues[samp] > 0.0 && audioValues[samp + 1] < 0.0)
				count++;
			else if (audioValues[samp] < 0.0 && audioValues[samp + 1] > 0.0)
				count++;
			else if (audioValues[samp] == 0.0 && audioValues[samp + 1] != 0.0)
				count++;
		}
		double result = 0;
		result = count;
		return result;
	}

	public double[] MFCCs(double[] audioValues_FFT, double sampleRate) {
		return null;
	}

}
