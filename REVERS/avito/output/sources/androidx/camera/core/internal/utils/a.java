package androidx.camera.core.internal.utils;

import androidx.camera.camera2.internal.G;
import androidx.camera.core.InterfaceC20120j0;
import j.B;
import j.N;
import j.P;
import java.util.ArrayDeque;

/* compiled from: ArrayRingBuffer.java */
/* loaded from: classes.dex */
public class a<T> implements b<T> {

    /* renamed from: a, reason: collision with root package name */
    public final int f24366a;

    /* renamed from: b, reason: collision with root package name */
    @B
    public final ArrayDeque<T> f24367b;

    /* renamed from: c, reason: collision with root package name */
    public final Object f24368c = new Object();

    /* renamed from: d, reason: collision with root package name */
    @P
    public final G f24369d;

    public a(int i12, @P G g12) {
        this.f24366a = i12;
        this.f24367b = new ArrayDeque<>(i12);
        this.f24369d = g12;
    }

    @N
    public final T a() {
        T tRemoveLast;
        synchronized (this.f24368c) {
            tRemoveLast = this.f24367b.removeLast();
        }
        return tRemoveLast;
    }

    public void b(@N T t12) throws Exception {
        T tA2;
        synchronized (this.f24368c) {
            try {
                tA2 = this.f24367b.size() >= this.f24366a ? a() : null;
                this.f24367b.addFirst(t12);
            } catch (Throwable th2) {
                throw th2;
            }
        }
        G g12 = this.f24369d;
        if (g12 == null || tA2 == null) {
            return;
        }
        g12.getClass();
        ((InterfaceC20120j0) tA2).close();
    }

    public final boolean c() {
        boolean zIsEmpty;
        synchronized (this.f24368c) {
            zIsEmpty = this.f24367b.isEmpty();
        }
        return zIsEmpty;
    }
}
