package androidx.camera.view;

import androidx.camera.core.C20144v;
import androidx.camera.core.K0;
import androidx.camera.view.PreviewView;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class t implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f25470b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f25471c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f25472d;

    public /* synthetic */ t(int i12, Object obj, Object obj2) {
        this.f25470b = i12;
        this.f25472d = obj;
        this.f25471c = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f25470b) {
            case 0:
                ((PreviewView.a) PreviewView.this.f25393o).a((K0) this.f25471c);
                break;
            case 1:
                I i12 = (I) this.f25472d;
                K0 k02 = i12.f25374h;
                if (k02 != null && k02 == ((K0) this.f25471c)) {
                    i12.f25374h = null;
                    i12.f25373g = null;
                }
                n nVar = i12.f25378l;
                if (nVar != null) {
                    nVar.b();
                    i12.f25378l = null;
                    break;
                }
                break;
            default:
                ((C20197i) this.f25472d).f25415a = (C20144v) this.f25471c;
                break;
        }
    }
}
