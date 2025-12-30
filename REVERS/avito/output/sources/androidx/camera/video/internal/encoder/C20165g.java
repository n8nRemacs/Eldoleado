package androidx.camera.video.internal.encoder;

import android.util.Range;
import androidx.concurrent.futures.b;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.video.internal.encoder.g, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class C20165g implements b.c {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f25161b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ AtomicReference f25162c;

    public /* synthetic */ C20165g(AtomicReference atomicReference, int i12) {
        this.f25161b = i12;
        this.f25162c = atomicReference;
    }

    @Override // androidx.concurrent.futures.b.c
    public final Object d(b.a aVar) {
        AtomicReference atomicReference = this.f25162c;
        switch (this.f25161b) {
            case 0:
                atomicReference.set(aVar);
                return "Data closed";
            case 1:
                atomicReference.set(aVar);
                return "Data closed";
            case 2:
                Range<Long> range = v.f25192D;
                atomicReference.set(aVar);
                return "acquireInputBuffer";
            case 3:
                Range<Long> range2 = v.f25192D;
                atomicReference.set(aVar);
                return "mReleasedFuture";
            default:
                atomicReference.set(aVar);
                return "Terminate InputBuffer";
        }
    }
}
