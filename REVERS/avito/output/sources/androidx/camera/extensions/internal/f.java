package androidx.camera.extensions.internal;

import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.UseCaseConfigFactory;
import j.N;
import j.P;
import j.X;

/* compiled from: ExtensionsUseCaseConfigFactory.java */
@X
/* loaded from: classes.dex */
public final class f implements UseCaseConfigFactory {

    /* compiled from: ExtensionsUseCaseConfigFactory.java */
    public static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f24635a;

        static {
            int[] iArr = new int[UseCaseConfigFactory.CaptureType.values().length];
            f24635a = iArr;
            try {
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f24635a[1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f24635a[2] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f24635a[3] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    @Override // androidx.camera.core.impl.UseCaseConfigFactory
    @P
    public final Config a(@N UseCaseConfigFactory.CaptureType captureType, int i12) {
        int iOrdinal = captureType.ordinal();
        if (iOrdinal == 0 || iOrdinal == 1 || iOrdinal == 2) {
            throw null;
        }
        if (iOrdinal != 3) {
            return null;
        }
        throw new IllegalArgumentException("CameraX Extensions doesn't support VideoCapture!");
    }
}
