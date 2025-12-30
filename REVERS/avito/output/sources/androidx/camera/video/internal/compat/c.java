package androidx.camera.video.internal.compat;

import android.media.MediaMuxer;
import j.InterfaceC42164u;
import j.N;
import j.X;
import java.io.FileDescriptor;

/* compiled from: Api26Impl.java */
@X
/* loaded from: classes.dex */
public final class c {
    @InterfaceC42164u
    @N
    public static MediaMuxer a(@N FileDescriptor fileDescriptor, int i12) {
        return new MediaMuxer(fileDescriptor, i12);
    }
}
