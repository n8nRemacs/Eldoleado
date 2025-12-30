package androidx.camera.core.imagecapture;

import androidx.camera.core.InterfaceC20120j0;
import java.nio.ByteBuffer;

/* compiled from: RgbaImageProxy.java */
/* loaded from: classes.dex */
class E implements InterfaceC20120j0.a {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f23781a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ByteBuffer f23782b;

    public E(int i12, ByteBuffer byteBuffer) {
        this.f23781a = i12;
        this.f23782b = byteBuffer;
    }

    @Override // androidx.camera.core.InterfaceC20120j0.a
    @j.N
    public final ByteBuffer v() {
        return this.f23782b;
    }

    @Override // androidx.camera.core.InterfaceC20120j0.a
    public final int w() {
        return this.f23781a;
    }

    @Override // androidx.camera.core.InterfaceC20120j0.a
    public final int x() {
        return 4;
    }
}
