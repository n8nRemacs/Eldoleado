package androidx.camera.core;

import android.graphics.Rect;
import android.media.Image;
import androidx.annotation.RestrictTo;
import androidx.camera.core.InterfaceC20120j0;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: ForwardingImageProxy.java */
@RestrictTo
@j.X
/* loaded from: classes.dex */
public abstract class O implements InterfaceC20120j0 {

    /* renamed from: c, reason: collision with root package name */
    public final InterfaceC20120j0 f23633c;

    /* renamed from: b, reason: collision with root package name */
    public final Object f23632b = new Object();

    /* renamed from: d, reason: collision with root package name */
    @j.B
    public final HashSet f23634d = new HashSet();

    /* compiled from: ForwardingImageProxy.java */
    public interface a {
        void e(@j.N O o12);
    }

    public O(@j.N InterfaceC20120j0 interfaceC20120j0) {
        this.f23633c = interfaceC20120j0;
    }

    @Override // androidx.camera.core.InterfaceC20120j0
    @j.N
    public Rect V2() {
        return this.f23633c.V2();
    }

    public final void a(@j.N a aVar) {
        synchronized (this.f23632b) {
            this.f23634d.add(aVar);
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        HashSet hashSet;
        this.f23633c.close();
        synchronized (this.f23632b) {
            hashSet = new HashSet(this.f23634d);
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            ((a) it.next()).e(this);
        }
    }

    @Override // androidx.camera.core.InterfaceC20120j0
    @j.N
    public InterfaceC20120j0.a[] e4() {
        return this.f23633c.e4();
    }

    @Override // androidx.camera.core.InterfaceC20120j0
    public int getHeight() {
        return this.f23633c.getHeight();
    }

    @Override // androidx.camera.core.InterfaceC20120j0
    @G
    @j.P
    public final Image getImage() {
        return this.f23633c.getImage();
    }

    @Override // androidx.camera.core.InterfaceC20120j0
    public int getWidth() {
        return this.f23633c.getWidth();
    }

    @Override // androidx.camera.core.InterfaceC20120j0
    @j.N
    public InterfaceC20060g0 r1() {
        return this.f23633c.r1();
    }

    @Override // androidx.camera.core.InterfaceC20120j0
    public final int w() {
        return this.f23633c.w();
    }
}
