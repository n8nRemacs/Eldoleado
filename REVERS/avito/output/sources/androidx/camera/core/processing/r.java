package androidx.camera.core.processing;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Size;
import androidx.camera.core.InterfaceC20120j0;
import j.N;
import j.P;
import j.X;

/* compiled from: Packet.java */
@VY0.c
@X
/* loaded from: classes.dex */
public abstract class r<T> {
    @N
    public static r<Bitmap> i(@N Bitmap bitmap, @N androidx.camera.core.impl.utils.i iVar, @N Rect rect, int i12, @N Matrix matrix, @N androidx.camera.core.impl.r rVar) {
        return new C20135c(bitmap, iVar, 42, new Size(bitmap.getWidth(), bitmap.getHeight()), rect, i12, matrix, rVar);
    }

    @N
    public static r<InterfaceC20120j0> j(@N InterfaceC20120j0 interfaceC20120j0, @P androidx.camera.core.impl.utils.i iVar, @N Size size, @N Rect rect, int i12, @N Matrix matrix, @N androidx.camera.core.impl.r rVar) {
        if (interfaceC20120j0.w() == 256) {
            androidx.core.util.z.f(iVar, "JPEG image must have Exif.");
        }
        return new C20135c(interfaceC20120j0, iVar, interfaceC20120j0.w(), size, rect, i12, matrix, rVar);
    }

    @N
    public static r k(@N byte[] bArr, @N androidx.camera.core.impl.utils.i iVar, @N Size size, @N Rect rect, int i12, @N Matrix matrix, @N androidx.camera.core.impl.r rVar) {
        return new C20135c(bArr, iVar, 256, size, rect, i12, matrix, rVar);
    }

    @N
    public abstract androidx.camera.core.impl.r a();

    @N
    public abstract Rect b();

    @N
    public abstract T c();

    @P
    public abstract androidx.camera.core.impl.utils.i d();

    public abstract int e();

    public abstract int f();

    @N
    public abstract Matrix g();

    @N
    public abstract Size h();
}
