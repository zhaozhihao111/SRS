package com.srs.dao;

import java.util.List;

import com.srs.model.TranscriptEntry;

public interface TranscriptDao {
	/**
	 * ��ȡ���е�Transcript����
	 * @return
	 */
	public List<TranscriptEntry> getTranscript();
}
