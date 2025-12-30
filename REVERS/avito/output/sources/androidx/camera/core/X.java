package androidx.camera.core;

import androidx.camera.core.impl.InterfaceC20092f0;

/* compiled from: ImageAnalysisBlockingAnalyzer.java */
@j.X
/* loaded from: classes.dex */
final class X extends W {

    /* compiled from: ImageAnalysisBlockingAnalyzer.java */
    public class a implements androidx.camera.core.impl.utils.futures.c<Void> {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ InterfaceC20120j0 f23694a;

        public a(InterfaceC20120j0 interfaceC20120j0) {
            this.f23694a = interfaceC20120j0;
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final void a(@j.N Throwable th2) throws Exception {
            this.f23694a.close();
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final /* bridge */ /* synthetic */ void onSuccess(Void r12) {
        }
    }

    @Override // androidx.camera.core.W
    @j.P
    public final InterfaceC20120j0 b(@j.N InterfaceC20092f0 interfaceC20092f0) {
        return interfaceC20092f0.a();
    }

    @Override // androidx.camera.core.W
    public final void f(@j.N InterfaceC20120j0 interfaceC20120j0) {
        androidx.camera.core.impl.utils.futures.f.a(c(interfaceC20120j0), new a(interfaceC20120j0), androidx.camera.core.impl.utils.executor.c.a());
    }

    @Override // androidx.camera.core.W
    public final void d() {
    }
}
