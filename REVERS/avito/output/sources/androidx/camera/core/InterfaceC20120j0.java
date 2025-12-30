package androidx.camera.core;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.media.Image;
import java.nio.ByteBuffer;

/* compiled from: ImageProxy.java */
@j.X
/* renamed from: androidx.camera.core.j0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public interface InterfaceC20120j0 extends AutoCloseable {

    /* compiled from: ImageProxy.java */
    /* renamed from: androidx.camera.core.j0$a */
    public interface a {
        @j.N
        ByteBuffer v();

        int w();

        int x();
    }

    @j.N
    Rect V2();

    @j.N
    @SuppressLint({"ArrayReturn"})
    a[] e4();

    int getHeight();

    @G
    @j.P
    Image getImage();

    int getWidth();

    @j.N
    InterfaceC20060g0 r1();

    int w();
}
