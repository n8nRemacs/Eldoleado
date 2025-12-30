package androidx.camera.core;

import androidx.camera.core.O;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.core.h0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class C20062h0 implements O.a {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f23744a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ InterfaceC20120j0 f23745b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ InterfaceC20120j0 f23746c;

    public /* synthetic */ C20062h0(InterfaceC20120j0 interfaceC20120j0, InterfaceC20120j0 interfaceC20120j02, int i12) {
        this.f23744a = i12;
        this.f23745b = interfaceC20120j0;
        this.f23746c = interfaceC20120j02;
    }

    @Override // androidx.camera.core.O.a
    public final void e(O o12) throws Exception {
        InterfaceC20120j0 interfaceC20120j0 = this.f23746c;
        switch (this.f23744a) {
            case 0:
                int i12 = ImageProcessingUtil.f23579a;
                interfaceC20120j0.close();
                break;
            default:
                int i13 = ImageProcessingUtil.f23579a;
                interfaceC20120j0.close();
                break;
        }
    }
}
