package androidx.camera.view;

import android.view.Surface;
import androidx.camera.core.C20140q0;
import androidx.camera.core.K0;
import androidx.concurrent.futures.b;
import androidx.core.util.C22807v;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.view.j, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class C20198j implements b.c {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f25441b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f25442c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f25443d;

    public /* synthetic */ C20198j(int i12, Object obj, Object obj2) {
        this.f25441b = i12;
        this.f25442c = obj;
        this.f25443d = obj2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.concurrent.futures.b.c
    public final Object d(b.a aVar) {
        switch (this.f25441b) {
            case 0:
                l lVar = (l) this.f25442c;
                C22807v<b.a<Void>, T> c22807v = lVar.f25444a;
                if (c22807v != 0) {
                    b.a aVar2 = (b.a) c22807v.f44895a;
                    Objects.requireNonNull(aVar2);
                    aVar2.c();
                }
                Object obj = this.f25443d;
                lVar.f25444a = new C22807v<>(aVar, obj);
                return k.a(obj, "PendingValue ");
            default:
                I i12 = (I) this.f25442c;
                i12.getClass();
                C20140q0.d(3, "TextureViewImpl");
                K0 k02 = i12.f25374h;
                Executor executorA = androidx.camera.core.impl.utils.executor.c.a();
                F f12 = new F(aVar, 1);
                Surface surface = (Surface) this.f25443d;
                k02.a(surface, executorA, f12);
                return "provideSurface[request=" + i12.f25374h + " surface=" + surface + "]";
        }
    }
}
