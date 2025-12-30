package androidx.camera.view;

import androidx.camera.view.z;
import androidx.concurrent.futures.b;
import com.google.common.util.concurrent.D0;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class m implements androidx.camera.core.impl.utils.futures.a, b.c, z.b {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f25445b;

    @Override // androidx.camera.core.impl.utils.futures.a, com.google.common.base.InterfaceC37276u
    public D0 apply(Object obj) {
        return ((q) this.f25445b).f25457d.g();
    }

    @Override // androidx.concurrent.futures.b.c
    public Object d(b.a aVar) {
        ((I) this.f25445b).f25377k.set(aVar);
        return "textureViewImpl_waitForNextFrame";
    }
}
