package androidx.camera.video.internal.audio;

import androidx.camera.video.internal.audio.i;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class e implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24959b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ i f24960c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ boolean f24961d;

    public /* synthetic */ e(i iVar, boolean z12, int i12) {
        this.f24959b = i12;
        this.f24960c = iVar;
        this.f24961d = z12;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f24959b) {
            case 0:
                i iVar = this.f24960c;
                int iOrdinal = iVar.f24975g.ordinal();
                if (iOrdinal != 0) {
                    if (iOrdinal == 2) {
                        throw new AssertionError("AudioSource is released");
                    }
                    return;
                }
                iVar.f24970b.set(null);
                iVar.f24971c.set(false);
                iVar.c(i.d.f24994c);
                iVar.f24969a.execute(new e(iVar, this.f24961d, 1));
                iVar.e();
                return;
            default:
                i iVar2 = this.f24960c;
                int iOrdinal2 = iVar2.f24975g.ordinal();
                if (iOrdinal2 != 0 && iOrdinal2 != 1) {
                    if (iOrdinal2 == 2) {
                        throw new AssertionError("AudioSource is released");
                    }
                    return;
                }
                boolean z12 = iVar2.f24986r;
                boolean z13 = this.f24961d;
                if (z12 == z13) {
                    return;
                }
                iVar2.f24986r = z13;
                if (iVar2.f24975g == i.d.f24994c) {
                    iVar2.a();
                    return;
                }
                return;
        }
    }
}
