package androidx.camera.core.internal.compat;

import android.media.ImageWriter;
import android.view.Surface;
import j.X;

/* compiled from: ImageWriterCompatApi26Impl.java */
@X
/* loaded from: classes.dex */
final class c {
    static {
        try {
            Class cls = Integer.TYPE;
            ImageWriter.class.getMethod("newInstance", Surface.class, cls, cls);
        } catch (NoSuchMethodException unused) {
        }
    }
}
