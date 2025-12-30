package androidx.camera.video.internal.compat;

import android.media.AudioRecord;
import android.media.AudioRecordingConfiguration;
import android.media.AudioTimestamp;
import j.InterfaceC42164u;
import j.N;
import j.X;

/* compiled from: Api24Impl.java */
@X
/* loaded from: classes.dex */
public final class b {
    @InterfaceC42164u
    public static int a(@N AudioRecordingConfiguration audioRecordingConfiguration) {
        return audioRecordingConfiguration.getClientAudioSessionId();
    }

    @InterfaceC42164u
    public static int b(@N AudioRecord audioRecord, @N AudioTimestamp audioTimestamp, int i12) {
        return audioRecord.getTimestamp(audioTimestamp, i12);
    }
}
