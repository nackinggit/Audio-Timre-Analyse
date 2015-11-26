package com.timbreanalyse.web;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.timbreanalyse.constant.Constant.MusicType;
import com.timbreanalyse.model.Music;
import com.timbreanalyse.model.MusicFeatures;
import com.timbreanalyse.tools.MP3DataExtractor;
import com.timbreanalyse.util.CommonUtil;

@Controller
@RequestMapping("/MP3")
public class MP3AnalyseController {
	@RequestMapping("/submit")
	public Object metaInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			SQLException {
		
		String filePath = request.getParameter("filePath");
		File file = new File(filePath);
		MP3AudioHeader audioHeader = MP3DataExtractor.audioHeaderExtractor(file);
		Music music = initMsc(audioHeader);
		music.setMscPath(filePath);
		String[] strs = filePath.split("\\");
		music.setMscName(strs[strs.length - 1]);
		
		ModelAndView mav = new ModelAndView("analyseResult",CommonUtil.asMap("musicInfo",music));
		return mav;
	}

	private Music initMsc(MP3AudioHeader audioHeader) {
		Music mscSequence = new Music();
		mscSequence.setConsistantTime(audioHeader.getTrackLength());
		mscSequence.setSmcType(MusicType.MP3);
		mscSequence.setBitrate(audioHeader.getBitRateAsNumber());
		mscSequence.setSampleRate(audioHeader.getSampleRateAsNumber());
		return mscSequence;
	}
	
	@RequestMapping("/analyse")
	public Object analyse(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String filePath = request.getParameter("filePath");
		File file = new File(filePath);
		MP3File mp3File = new MP3File(file);
		int sampleRate = mp3File.getMP3AudioHeader().getSampleRateAsNumber();
		double[] timeSequence = MP3DataExtractor.audioValuesExtractor(file);
		MusicFeatures features = new MusicFeatures(timeSequence, sampleRate);
		ModelAndView mav = new ModelAndView("analyzeRes", CommonUtil.asMap("features",features));
		return mav;
	}
	
}
