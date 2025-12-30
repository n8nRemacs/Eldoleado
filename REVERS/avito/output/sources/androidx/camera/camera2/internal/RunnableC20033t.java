package androidx.camera.camera2.internal;

import androidx.camera.camera2.internal.C20039w;
import androidx.camera.core.impl.C20094g0;
import androidx.camera.core.impl.SessionConfig;
import java.util.Arrays;
import java.util.HashSet;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.camera2.internal.t, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC20033t implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23434b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ C20039w f23435c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f23436d;

    /* renamed from: e, reason: collision with root package name */
    public final /* synthetic */ Object f23437e;

    /* renamed from: f, reason: collision with root package name */
    public final /* synthetic */ Object f23438f;

    public /* synthetic */ RunnableC20033t(C20039w c20039w, Object obj, Object obj2, Object obj3, int i12) {
        this.f23434b = i12;
        this.f23435c = c20039w;
        this.f23436d = obj;
        this.f23437e = obj2;
        this.f23438f = obj3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f23434b) {
            case 0:
                C20039w c20039w = this.f23435c;
                String str = (String) this.f23436d;
                SessionConfig sessionConfig = (SessionConfig) this.f23437e;
                androidx.camera.core.impl.L0<?> l02 = (androidx.camera.core.impl.L0) this.f23438f;
                c20039w.getClass();
                c20039w.s("Use case " + str + " RESET");
                c20039w.f23466b.k(str, sessionConfig, l02);
                c20039w.p();
                c20039w.D();
                c20039w.K();
                if (c20039w.f23470f == C20039w.f.f23501e) {
                    c20039w.A();
                    break;
                }
                break;
            case 1:
                C20039w c20039w2 = this.f23435c;
                c20039w2.getClass();
                StringBuilder sb2 = new StringBuilder("Use case ");
                String str2 = (String) this.f23436d;
                c20039w2.s(AK.c.s(sb2, str2, " UPDATED"));
                c20039w2.f23466b.k(str2, (SessionConfig) this.f23437e, (androidx.camera.core.impl.L0) this.f23438f);
                c20039w2.K();
                break;
            case 2:
                C20039w c20039w3 = this.f23435c;
                c20039w3.getClass();
                StringBuilder sb3 = new StringBuilder("Use case ");
                String str3 = (String) this.f23436d;
                c20039w3.s(AK.c.s(sb3, str3, " ACTIVE"));
                androidx.camera.core.impl.K0 k02 = c20039w3.f23466b;
                SessionConfig sessionConfig2 = (SessionConfig) this.f23437e;
                androidx.camera.core.impl.L0<?> l03 = (androidx.camera.core.impl.L0) this.f23438f;
                k02.g(str3, sessionConfig2, l03);
                k02.k(str3, sessionConfig2, l03);
                c20039w3.K();
                break;
            default:
                C20039w c20039w4 = this.f23435c;
                HashSet hashSet = c20039w4.f23483s;
                C20003d0 c20003d0 = (C20003d0) this.f23436d;
                hashSet.remove(c20003d0);
                com.google.common.util.concurrent.D0 d0B = c20039w4.B(c20003d0);
                C20094g0 c20094g0 = (C20094g0) this.f23437e;
                c20094g0.a();
                androidx.camera.core.impl.utils.futures.f.k(Arrays.asList(d0B, androidx.camera.core.impl.utils.futures.f.h(c20094g0.f23933e))).N((RunnableC20016k) this.f23438f, androidx.camera.core.impl.utils.executor.c.a());
                break;
        }
    }
}
