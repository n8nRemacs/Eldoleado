package androidx.camera.core.internal.utils;

import android.util.Size;
import j.N;
import j.X;

/* compiled from: SizeUtil.java */
@X
/* loaded from: classes.dex */
public final class c {

    /* renamed from: a, reason: collision with root package name */
    public static final Size f24370a = new Size(0, 0);

    /* renamed from: b, reason: collision with root package name */
    public static final Size f24371b = new Size(320, 240);

    /* renamed from: c, reason: collision with root package name */
    public static final Size f24372c = new Size(640, 480);

    /* renamed from: d, reason: collision with root package name */
    public static final Size f24373d = new Size(720, 480);

    /* renamed from: e, reason: collision with root package name */
    public static final Size f24374e = new Size(1280, 720);

    /* renamed from: f, reason: collision with root package name */
    public static final Size f24375f = new Size(1920, 1080);

    /* renamed from: g, reason: collision with root package name */
    public static final Size f24376g = new Size(1920, 1440);

    public static int a(@N Size size) {
        return size.getHeight() * size.getWidth();
    }
}
