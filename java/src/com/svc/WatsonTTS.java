package com.svc;

import java.io.InputStream;

import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.GetPronunciationOptions.Voice;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.SynthesizeOptions;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.SynthesizeOptions.Accept;
import com.ibm.watson.developer_cloud.text_to_speech.v1.util.WaveUtils;
import com.klab.svc.AppsPropertiy;

/**
 * @author 최의신 (choies@kr.ibm.com)
 * 
 * Watson TTS 서비스를 사용한다.
 *
 */
public class WatsonTTS implements ITts
{
	TextToSpeech service;
	
	public WatsonTTS()
	{
		service = new TextToSpeech();
		service.setUsernameAndPassword(AppsPropertiy.getInstance().getProperty("tts.watson.user"), AppsPropertiy.getInstance().getProperty("tts.watson.passwd")); 
	}
	
	@Override
	public InputStream streamTTS(String text)
	{
		if ( text == null || text.length() == 0 )
            return null;

        InputStream is = null;

        try {
        	SynthesizeOptions opt = new SynthesizeOptions.Builder()
        			.text(text)
        			.accept(Accept.AUDIO_WAV)
        			.voice(Voice.EN_US_ALLISONVOICE)
        			.build();
        	InputStream stream = service.synthesize(opt).execute();
        	is = WaveUtils. reWriteWaveHeader(stream);
        	stream.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }

        return is;
	}

	@Override
	public String fileTTS(String text) {
		return null;
	}
}
