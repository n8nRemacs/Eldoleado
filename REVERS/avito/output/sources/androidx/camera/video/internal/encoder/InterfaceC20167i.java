package androidx.camera.video.internal.encoder;

import android.media.MediaCodec;
import j.X;
import java.nio.ByteBuffer;

/* compiled from: EncodedData.java */
@X
/* renamed from: androidx.camera.video.internal.encoder.i, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public interface InterfaceC20167i extends AutoCloseable {
    @j.N
    MediaCodec.BufferInfo C0();

    @j.N
    ByteBuffer E();

    boolean M3();

    long S0();

    long size();
}
