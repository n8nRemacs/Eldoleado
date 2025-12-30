package androidx.camera.video.internal.encoder;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class A implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f25107b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ InterfaceC20170l f25108c;

    public /* synthetic */ A(InterfaceC20170l interfaceC20170l, int i12) {
        this.f25107b = i12;
        this.f25108c = interfaceC20170l;
    }

    @Override // java.lang.Runnable
    public final void run() {
        InterfaceC20170l interfaceC20170l = this.f25108c;
        switch (this.f25107b) {
            case 0:
                interfaceC20170l.getClass();
                InterfaceC20170l interfaceC20170l2 = InterfaceC20170l.f25174a;
                break;
            case 1:
                interfaceC20170l.getClass();
                break;
            default:
                interfaceC20170l.b();
                break;
        }
    }
}
