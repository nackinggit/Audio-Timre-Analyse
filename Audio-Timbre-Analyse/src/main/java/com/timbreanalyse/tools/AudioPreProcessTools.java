package com.timbreanalyse.tools;


public class AudioPreProcessTools {
	/**
	 * 信号分帧，使用矩形窗
	 * @param timeSequence
	 * @param sampleRate
	 * @param len2nd
	 * @param step
	 * @return
	 */
	public static double[][] enframe(double[] timeSequence, int len2nd, int step) {
		
		if(timeSequence == null || timeSequence.length < 1)  return null;
		
		int len1st = (timeSequence.length - len2nd + step)/step;
		double[][] frames = new double[len1st][];
		int index = 0;
		for(int i = 0; i < len1st; i++) {
			frames[i] = new double[len2nd];
			for(int j = 0; j < len2nd; j++) {
				frames[i][j] = timeSequence[index++];
			}
			index -= (len2nd - step);
		}
		
		return frames;
	} 
	
	/**
	 * 对输入的二维数组每列取均值
	 * @param values
	 * @return
	 */
	public static double[] mean(double[][] values) {
		if(values == null) return null;
		double[] result = new double[values[0].length];
		for(int i = 0; i < result.length; i++) {
			double sum = 0;
			for(int j = 0; j < values.length; j++) {
				sum += values[j][i];
			}
			result[i] = sum/values.length;
		}
		return result;
	}
	/*public static void main(String[] args) {
		double[] a = {1,2,3,4,5};
		double[][] b = enframe(a, 0, 3, 2);
		for(int i = 0; i<b.length;i++)
		System.out.println(Arrays.toString(b[i]));
	}*/
}
