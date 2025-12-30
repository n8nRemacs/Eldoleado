package androidx.camera.core;

import androidx.camera.core.InterfaceC20120j0;

/* compiled from: ImageProxyDownsampler.java */
@j.X
/* renamed from: androidx.camera.core.l0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20124l0 {

    /* compiled from: ImageProxyDownsampler.java */
    /* renamed from: androidx.camera.core.l0$a */
    public static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f24383a;

        static {
            int[] iArr = new int[b.values().length];
            f24383a = iArr;
            try {
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f24383a[1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: ImageProxyDownsampler.java */
    /* renamed from: androidx.camera.core.l0$b */
    public static final class b {

        /* renamed from: b, reason: collision with root package name */
        public static final /* synthetic */ b[] f24384b = {new b("NEAREST_NEIGHBOR", 0), new b("AVERAGING", 1)};

        /* JADX INFO: Fake field, exist only in values array */
        b EF5;

        public b() {
            throw null;
        }

        public static b valueOf(String str) {
            return (b) Enum.valueOf(b.class, str);
        }

        public static b[] values() {
            return (b[]) f24384b.clone();
        }
    }

    /* compiled from: ImageProxyDownsampler.java */
    /* renamed from: androidx.camera.core.l0$c */
    public static final class c extends O {
        @Override // androidx.camera.core.O, androidx.camera.core.InterfaceC20120j0
        @j.N
        public final InterfaceC20120j0.a[] e4() {
            return null;
        }

        @Override // androidx.camera.core.O, androidx.camera.core.InterfaceC20120j0
        public final int getHeight() {
            return 0;
        }

        @Override // androidx.camera.core.O, androidx.camera.core.InterfaceC20120j0
        public final int getWidth() {
            return 0;
        }
    }
}
