package androidx.camera.core;

import androidx.camera.core.T;
import androidx.camera.core.impl.InterfaceC20092f0;
import androidx.camera.core.v0;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.core.c, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC20051c implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23715b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f23716c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f23717d;

    public /* synthetic */ RunnableC20051c(int i12, Object obj, Object obj2) {
        this.f23715b = i12;
        this.f23716c = obj;
        this.f23717d = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Object obj = this.f23717d;
        Object obj2 = this.f23716c;
        switch (this.f23715b) {
            case 0:
                C20053d c20053d = (C20053d) obj2;
                c20053d.getClass();
                ((InterfaceC20092f0.a) obj).a(c20053d);
                break;
            case 1:
                T.d dVar = T.f23653s;
                ((y0) obj2).e();
                y0 y0Var = (y0) obj;
                if (y0Var != null) {
                    y0Var.e();
                    break;
                }
                break;
            case 2:
                r0 r0Var = (r0) obj2;
                r0Var.getClass();
                ((InterfaceC20092f0.a) obj).a(r0Var);
                break;
            default:
                v0.b bVar = v0.f24566t;
                ((v0.c) obj2).a((K0) obj);
                break;
        }
    }
}
