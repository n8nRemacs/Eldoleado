package androidx.camera.core;

import android.util.Range;
import androidx.camera.core.K0;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class E0 implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23558b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ K0.e f23559c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ K0.d f23560d;

    public /* synthetic */ E0(K0.e eVar, K0.d dVar, int i12) {
        this.f23558b = i12;
        this.f23559c = eVar;
        this.f23560d = dVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        K0.d dVar = this.f23560d;
        K0.e eVar = this.f23559c;
        switch (this.f23558b) {
            case 0:
                Range<Integer> range = K0.f23588o;
                eVar.a(dVar);
                break;
            default:
                Range<Integer> range2 = K0.f23588o;
                eVar.a(dVar);
                break;
        }
    }
}
