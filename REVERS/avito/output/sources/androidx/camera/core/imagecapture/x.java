package androidx.camera.core.imagecapture;

import android.util.ArrayMap;
import android.util.Pair;
import android.util.Size;
import android.view.Surface;
import androidx.camera.core.InterfaceC20120j0;
import androidx.camera.core.impl.InterfaceC20092f0;
import androidx.camera.core.impl.J0;
import androidx.camera.core.z0;
import j.X;
import java.util.concurrent.Executor;

/* compiled from: NoMetadataImageReader.java */
@X
/* loaded from: classes.dex */
public class x implements InterfaceC20092f0 {

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public final InterfaceC20092f0 f23864a;

    /* renamed from: b, reason: collision with root package name */
    @j.P
    public B f23865b;

    public x(@j.N InterfaceC20092f0 interfaceC20092f0) {
        this.f23864a = interfaceC20092f0;
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    @j.P
    public final InterfaceC20120j0 a() {
        return e(this.f23864a.a());
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    @j.P
    public final InterfaceC20120j0 b() {
        return e(this.f23864a.b());
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final void c() {
        this.f23864a.c();
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final void close() {
        this.f23864a.close();
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final void d(@j.N final InterfaceC20092f0.a aVar, @j.N Executor executor) {
        this.f23864a.d(new InterfaceC20092f0.a() { // from class: androidx.camera.core.imagecapture.w
            @Override // androidx.camera.core.impl.InterfaceC20092f0.a
            public final void a(InterfaceC20092f0 interfaceC20092f0) {
                x xVar = this.f23862b;
                xVar.getClass();
                aVar.a(xVar);
            }
        }, executor);
    }

    @j.P
    public final z0 e(@j.P InterfaceC20120j0 interfaceC20120j0) {
        if (interfaceC20120j0 == null) {
            return null;
        }
        androidx.core.util.z.g("Pending request should not be null", this.f23865b != null);
        B b12 = this.f23865b;
        Pair pair = new Pair(b12.f23768g, b12.f23769h.get(0));
        J0 j02 = J0.f23986b;
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put((String) pair.first, pair.second);
        J0 j03 = new J0(arrayMap);
        this.f23865b = null;
        return new z0(interfaceC20120j0, new Size(interfaceC20120j0.getWidth(), interfaceC20120j0.getHeight()), new androidx.camera.core.internal.c(new androidx.camera.core.streamsharing.i(null, j03, interfaceC20120j0.r1().a())));
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final int f() {
        return this.f23864a.f();
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final int g() {
        return this.f23864a.g();
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final int getHeight() {
        return this.f23864a.getHeight();
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    @j.P
    public final Surface getSurface() {
        return this.f23864a.getSurface();
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final int getWidth() {
        return this.f23864a.getWidth();
    }
}
