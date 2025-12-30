package androidx.camera.core;

import android.content.Context;
import android.os.SystemClock;
import android.util.Range;
import androidx.camera.core.impl.InterfaceC20092f0;
import androidx.concurrent.futures.b;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.core.w, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class C20145w implements b.c, InterfaceC20092f0.a {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24576b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f24577c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f24578d;

    public /* synthetic */ C20145w(int i12, Object obj, Object obj2) {
        this.f24576b = i12;
        this.f24577c = obj;
        this.f24578d = obj2;
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0.a
    public void a(InterfaceC20092f0 interfaceC20092f0) {
        y0 y0Var = (y0) this.f24577c;
        y0Var.getClass();
        ((InterfaceC20092f0.a) this.f24578d).a(y0Var);
    }

    @Override // androidx.concurrent.futures.b.c
    public Object d(b.a aVar) {
        Object obj = this.f24578d;
        Object obj2 = this.f24577c;
        switch (this.f24576b) {
            case 0:
                C20148z c20148z = (C20148z) obj2;
                Executor executor = c20148z.f24601d;
                executor.execute(new RunnableC20146x(c20148z, (Context) obj, executor, aVar, SystemClock.elapsedRealtime()));
                return "CameraX initInternal";
            default:
                Range<Integer> range = K0.f23588o;
                K0 k02 = (K0) obj2;
                k02.getClass();
                ((AtomicReference) obj).set(aVar);
                return "SurfaceRequest-surface-recreation(" + k02.hashCode() + ")";
        }
    }
}
