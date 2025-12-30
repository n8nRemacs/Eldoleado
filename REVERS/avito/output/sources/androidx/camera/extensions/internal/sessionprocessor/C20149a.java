package androidx.camera.extensions.internal.sessionprocessor;

import android.util.Size;
import j.N;

/* compiled from: AutoValue_ImageReaderOutputConfig.java */
/* renamed from: androidx.camera.extensions.internal.sessionprocessor.a, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20149a extends q {
    @Override // androidx.camera.extensions.internal.sessionprocessor.q
    public final int a() {
        return 0;
    }

    @Override // androidx.camera.extensions.internal.sessionprocessor.q
    public final int b() {
        return 0;
    }

    @Override // androidx.camera.extensions.internal.sessionprocessor.q
    @N
    public final Size c() {
        return null;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof q) {
            ((C20149a) ((q) obj)).getClass();
            Object obj2 = null;
            obj2.getClass();
        }
        return false;
    }

    public final int hashCode() {
        throw null;
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("ImageReaderOutputConfig{id=");
        sb2.append(0);
        sb2.append(", surfaceGroupId=");
        sb2.append(0);
        sb2.append(", physicalCameraId=");
        sb2.append((String) null);
        sb2.append(", surfaceSharingOutputConfigs=");
        sb2.append((Object) null);
        sb2.append(", size=");
        sb2.append((Object) null);
        sb2.append(", imageFormat=");
        sb2.append(0);
        sb2.append(", maxImages=");
        return AK.c.i(0, "}", sb2);
    }
}
