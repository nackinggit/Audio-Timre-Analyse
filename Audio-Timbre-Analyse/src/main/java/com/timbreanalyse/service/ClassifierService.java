package com.timbreanalyse.service;

import weka.classifiers.Classifier;

import com.timbreanalyse.constant.Constant.InstrumentType;

public interface ClassifierService {
	public InstrumentType classify(double[] audioValue, Classifier classifier);
}
