package androidx.camera.core.impl;

import android.view.Surface;
import androidx.annotation.RestrictTo;
import androidx.camera.core.InterfaceC20120j0;
import java.util.concurrent.Executor;

/* compiled from: ImageReaderProxy.java */
@j.X
/* renamed from: androidx.camera.core.impl.f0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public interface InterfaceC20092f0 {

    /* compiled from: ImageReaderProxy.java */
    @RestrictTo
    /* renamed from: androidx.camera.core.impl.f0$a */
    public interface a {
        void a(@j.N InterfaceC20092f0 interfaceC20092f0);
    }

    @j.P
    InterfaceC20120j0 a();

    @j.P
    InterfaceC20120j0 b();

    void c();

    void close();

    void d(@j.N a aVar, @j.N Executor executor);

    int f();

    int g();

    int getHeight();

    @j.P
    Surface getSurface();

    int getWidth();
}
