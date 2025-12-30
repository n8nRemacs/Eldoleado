package androidx.camera.core.impl;

import androidx.camera.core.impl.C20102k0;
import androidx.concurrent.futures.b;
import androidx.view.AbstractC22991Y;
import java.util.concurrent.Executor;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.core.impl.j0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC20100j0 implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24132b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f24133c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f24134d;

    /* renamed from: e, reason: collision with root package name */
    public final /* synthetic */ Object f24135e;

    public /* synthetic */ RunnableC20100j0(Object obj, Object obj2, Object obj3, int i12) {
        this.f24132b = i12;
        this.f24133c = obj;
        this.f24134d = obj2;
        this.f24135e = obj3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f24132b) {
            case 0:
                AbstractC22991Y abstractC22991Y = ((C20102k0) this.f24133c).f24145a;
                C20102k0.a aVar = (C20102k0.a) this.f24134d;
                if (aVar != null) {
                    abstractC22991Y.removeObserver(aVar);
                }
                abstractC22991Y.observeForever((C20102k0.a) this.f24135e);
                break;
            default:
                ((Executor) this.f24133c).execute(new RunnableC20098i0(3, (com.google.common.util.concurrent.D0) this.f24134d, (b.a) this.f24135e));
                break;
        }
    }
}
