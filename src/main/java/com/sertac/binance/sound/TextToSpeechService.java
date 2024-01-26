package com.sertac.binance.sound;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import org.springframework.stereotype.Service;

@Service
public class TextToSpeechService {

    public static void read(String text){
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        VoiceManager voiceManager = VoiceManager.getInstance();

        Voice voice = voiceManager.getVoice("kevin16");

        if (voice == null) {
            System.err.println("Voice is not found");
            return;
        }

        // Sesin özelliklerini ayarla
        voice.allocate();

        System.out.println("Played : " + text);
        // Metni sesli olarak oku
        voice.speak(text);

    }
}
