package com.timbreanalyse.model;

import com.timbreanalyse.constant.Constant.InstrumentType;
import com.timbreanalyse.constant.Constant.MusicType;

public class Music {
	private String mscName;
	private int mscId;
	private String mscPath;
	private String mscAuthorName;
	private String mscAlbum;
	private long consistantTime;
	private long bitrate;
	private long sampleRate;
	
	private MusicType smcType;
	private InstrumentType instrumentType;
	
	public InstrumentType getInstrumentType() {
		return instrumentType;
	}
	public void setInstrumentType(InstrumentType instrumentType) {
		this.instrumentType = instrumentType;
	}
	public String getMscName() {
		return mscName;
	}
	public void setMscName(String mscName) {
		this.mscName = mscName;
	}
	
	public int getMscId() {
		return mscId;
	}
	public void setMscId(int mscId) {
		this.mscId = mscId;
	}
	
	public String getMscPath() {
		return mscPath;
	}
	public void setMscPath(String mscPath) {
		this.mscPath = mscPath;
	}
	
	public String getMscAuthorName() {
		return mscAuthorName;
	}
	public void setMscAuthorName(String mscAuthorName) {
		this.mscAuthorName = mscAuthorName;
	}
	
	public String getMscAlbum() {
		return mscAlbum;
	}
	public void setMscAlbum(String mscAlbum) {
		this.mscAlbum = mscAlbum;
	}
	
	public long getConsistantTime() {
		return consistantTime;
	}
	public void setConsistantTime(long consistantTime) {
		this.consistantTime = consistantTime;
	}
	public MusicType getSmcType() {
		return smcType;
	}
	public void setSmcType(MusicType smcType) {
		this.smcType = smcType;
	}
	public long getBitrate() {
		return bitrate;
	}
	public void setBitrate(long bitrate) {
		this.bitrate = bitrate;
	}
	public long getSampleRate() {
		return sampleRate;
	}
	public void setSampleRate(long sampleRate) {
		this.sampleRate = sampleRate;
	}
}
