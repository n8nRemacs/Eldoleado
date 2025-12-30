package androidx.camera.core;

import android.graphics.Matrix;
import androidx.annotation.RestrictTo;
import androidx.camera.core.impl.utils.ExifData;

/* compiled from: ImmutableImageInfo.java */
@VY0.c
@RestrictTo
@j.X
/* renamed from: androidx.camera.core.p0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public abstract class AbstractC20132p0 implements InterfaceC20060g0 {
    @j.N
    public static InterfaceC20060g0 e(@j.N androidx.camera.core.impl.J0 j02, long j12, int i12, @j.N Matrix matrix) {
        return new C20063i(j02, j12, i12, matrix);
    }

    @Override // androidx.camera.core.InterfaceC20060g0
    public final void c(@j.N ExifData.b bVar) {
        bVar.e(((C20063i) this).f23749c);
    }

    @j.N
    public abstract Matrix f();
}
