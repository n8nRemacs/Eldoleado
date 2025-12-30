package androidx.camera.core.impl;

import androidx.camera.core.impl.Config;

/* compiled from: ImageInputConfig.java */
@j.X
/* renamed from: androidx.camera.core.impl.c0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public interface InterfaceC20086c0 extends x0 {

    /* renamed from: e, reason: collision with root package name */
    public static final Config.a<Integer> f24088e = Config.a.a(Integer.TYPE, "camerax.core.imageInput.inputFormat");

    /* renamed from: f, reason: collision with root package name */
    public static final Config.a<androidx.camera.core.F> f24089f = Config.a.a(androidx.camera.core.F.class, "camerax.core.imageInput.inputDynamicRange");

    /* compiled from: ImageInputConfig.java */
    /* renamed from: androidx.camera.core.impl.c0$a */
    public interface a<B> {
    }

    default boolean G() {
        return c(f24089f);
    }

    default int getInputFormat() {
        return ((Integer) f(f24088e)).intValue();
    }

    @j.N
    default androidx.camera.core.F o() {
        androidx.camera.core.F f12 = (androidx.camera.core.F) g(f24089f, androidx.camera.core.F.f23561c);
        f12.getClass();
        return f12;
    }
}
