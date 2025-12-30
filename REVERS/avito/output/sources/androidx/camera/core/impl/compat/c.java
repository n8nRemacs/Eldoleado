package androidx.camera.core.impl.compat;

import android.media.EncoderProfiles;
import androidx.camera.core.impl.S;
import j.N;
import j.X;
import java.util.ArrayList;
import java.util.List;

/* compiled from: EncoderProfilesProxyCompatApi33Impl.java */
@X
/* loaded from: classes.dex */
class c {
    @N
    public static S a(@N EncoderProfiles encoderProfiles) {
        int defaultDurationSeconds = encoderProfiles.getDefaultDurationSeconds();
        int recommendedFileFormat = encoderProfiles.getRecommendedFileFormat();
        List<EncoderProfiles.AudioProfile> audioProfiles = encoderProfiles.getAudioProfiles();
        ArrayList arrayList = new ArrayList();
        for (EncoderProfiles.AudioProfile audioProfile : audioProfiles) {
            arrayList.add(S.a.a(audioProfile.getCodec(), audioProfile.getBitrate(), audioProfile.getMediaType(), audioProfile.getSampleRate(), audioProfile.getChannels(), audioProfile.getProfile()));
        }
        List<EncoderProfiles.VideoProfile> videoProfiles = encoderProfiles.getVideoProfiles();
        ArrayList arrayList2 = new ArrayList();
        for (EncoderProfiles.VideoProfile videoProfile : videoProfiles) {
            arrayList2.add(S.c.a(videoProfile.getCodec(), videoProfile.getMediaType(), videoProfile.getBitrate(), videoProfile.getFrameRate(), videoProfile.getWidth(), videoProfile.getHeight(), videoProfile.getProfile(), videoProfile.getBitDepth(), videoProfile.getChromaSubsampling(), videoProfile.getHdrFormat()));
        }
        return S.b.e(defaultDurationSeconds, recommendedFileFormat, arrayList2, arrayList);
    }
}
