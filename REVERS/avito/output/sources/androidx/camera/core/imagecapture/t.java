package androidx.camera.core.imagecapture;

import android.net.Uri;
import androidx.camera.core.C20056e0;
import androidx.camera.core.ImageCaptureException;
import j.X;
import java.io.File;

/* compiled from: JpegBytes2Disk.java */
@X
/* loaded from: classes.dex */
class t implements androidx.camera.core.processing.q<a, C20056e0.m> {

    /* compiled from: JpegBytes2Disk.java */
    @VY0.c
    public static abstract class a {
        @j.N
        public abstract C20056e0.l a();

        @j.N
        public abstract androidx.camera.core.processing.r<byte[]> b();
    }

    public static Uri a(@j.N File file, @j.N File file2) throws ImageCaptureException {
        if (file2.exists()) {
            file2.delete();
        }
        if (file.renameTo(file2)) {
            return Uri.fromFile(file2);
        }
        throw new ImageCaptureException("Failed to overwrite the file: " + file2.getAbsolutePath(), null);
    }
}
