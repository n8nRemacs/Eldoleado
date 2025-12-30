package androidx.camera.video.internal.compat;

import android.media.AudioFormat;
import android.media.AudioRecord;
import j.InterfaceC42164u;
import j.N;
import j.X;
import j.a0;

/* compiled from: Api23Impl.java */
@X
/* loaded from: classes.dex */
public final class a {
    @InterfaceC42164u
    @N
    @a0
    public static AudioRecord a(@N AudioRecord.Builder builder) {
        return builder.build();
    }

    @InterfaceC42164u
    @N
    public static AudioRecord.Builder b() {
        return new AudioRecord.Builder();
    }

    @InterfaceC42164u
    public static void c(@N AudioRecord.Builder builder, @N AudioFormat audioFormat) throws IllegalArgumentException {
        builder.setAudioFormat(audioFormat);
    }

    @InterfaceC42164u
    public static void d(@N AudioRecord.Builder builder, int i12) throws IllegalArgumentException {
        builder.setAudioSource(i12);
    }

    @InterfaceC42164u
    public static void e(@N AudioRecord.Builder builder, int i12) throws IllegalArgumentException {
        builder.setBufferSizeInBytes(i12);
    }
}
