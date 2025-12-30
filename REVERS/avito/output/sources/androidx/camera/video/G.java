package androidx.camera.video;

import androidx.camera.video.O;
import androidx.camera.video.internal.encoder.EncodeException;
import androidx.concurrent.futures.b;
import androidx.core.util.InterfaceC22791e;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class G implements InterfaceC22791e {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ O f24696b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ b.a f24697c;

    public /* synthetic */ G(O o12, b.a aVar) {
        this.f24696b = o12;
        this.f24697c = aVar;
    }

    @Override // androidx.core.util.InterfaceC22791e
    public final void accept(Object obj) {
        Throwable th2 = (Throwable) obj;
        O o12 = this.f24696b;
        if (o12.f24745T == null) {
            if (th2 instanceof EncodeException) {
                o12.x(O.g.f24796f);
            } else {
                o12.x(O.g.f24797g);
            }
            o12.f24745T = th2;
            o12.H();
            this.f24697c.b(null);
        }
    }
}
