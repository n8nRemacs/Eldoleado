package androidx.camera.video;

import androidx.camera.core.impl.SessionConfig;
import androidx.camera.video.O;
import androidx.camera.video.O.b;
import androidx.camera.video.O.c;
import androidx.camera.video.O.d;
import androidx.camera.video.b0;
import androidx.concurrent.futures.b;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class J implements b.c {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24707b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f24708c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f24709d;

    public /* synthetic */ J(int i12, Object obj, Object obj2) {
        this.f24707b = i12;
        this.f24708c = obj;
        this.f24709d = obj2;
    }

    @Override // androidx.concurrent.futures.b.c
    public final Object d(b.a aVar) {
        Object obj = this.f24709d;
        Object obj2 = this.f24708c;
        switch (this.f24707b) {
            case 0:
                O o12 = (O) obj2;
                o12.f24728C.a(o12.new b(aVar, (O.i) obj), o12.f24755c);
                return "videoEncodingFuture";
            case 1:
                Set<O.j> set = O.f24719b0;
                O o13 = (O) obj2;
                o13.getClass();
                G g12 = new G(o13, aVar);
                androidx.camera.video.internal.audio.i iVar = o13.f24727B;
                Executor executor = o13.f24755c;
                iVar.f24969a.execute(new androidx.camera.video.internal.audio.c(iVar, executor, o13.new c(g12)));
                o13.f24730E.a(o13.new d(aVar, g12, (O.i) obj), executor);
                return "audioEncodingFuture";
            default:
                b0.c cVar = b0.f24861A;
                ((b0) obj2).getClass();
                SessionConfig.b bVar = (SessionConfig.b) obj;
                bVar.j(Integer.valueOf(aVar.hashCode()), "androidx.camera.video.VideoCapture.streamUpdate");
                AtomicBoolean atomicBoolean = new AtomicBoolean(false);
                c0 c0Var = new c0(atomicBoolean, aVar, bVar);
                aVar.a(new H(atomicBoolean, bVar, c0Var, 2), androidx.camera.core.impl.utils.executor.c.a());
                bVar.g(c0Var);
                return String.format("%s[0x%x]", "androidx.camera.video.VideoCapture.streamUpdate", Integer.valueOf(aVar.hashCode()));
        }
    }
}
