package androidx.camera.video.internal.compat;

import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioRecordingConfiguration;
import j.InterfaceC42164u;
import j.N;
import j.P;
import j.X;
import java.util.concurrent.Executor;

/* compiled from: Api29Impl.java */
@X
/* loaded from: classes.dex */
public final class e {
    @InterfaceC42164u
    @P
    public static AudioRecordingConfiguration a(@N AudioRecord audioRecord) {
        return audioRecord.getActiveRecordingConfiguration();
    }

    @InterfaceC42164u
    public static boolean b(@N AudioRecordingConfiguration audioRecordingConfiguration) {
        return audioRecordingConfiguration.isClientSilenced();
    }

    @InterfaceC42164u
    public static void c(@N AudioRecord audioRecord, @N Executor executor, @N AudioManager.AudioRecordingCallback audioRecordingCallback) {
        audioRecord.registerAudioRecordingCallback(executor, audioRecordingCallback);
    }

    @InterfaceC42164u
    public static void d(@N AudioRecord audioRecord, @N AudioManager.AudioRecordingCallback audioRecordingCallback) {
        audioRecord.unregisterAudioRecordingCallback(audioRecordingCallback);
    }
}
