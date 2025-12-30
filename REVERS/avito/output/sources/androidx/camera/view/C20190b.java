package androidx.camera.view;

import androidx.camera.view.PreviewView;
import q.InterfaceC47174a;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.view.b, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class C20190b implements InterfaceC47174a {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f25412a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f25413b;

    public /* synthetic */ C20190b(Object obj, int i12) {
        this.f25412a = i12;
        this.f25413b = obj;
    }

    @Override // q.InterfaceC47174a
    public final Object apply(Object obj) {
        switch (this.f25412a) {
            case 0:
                AbstractC20193e abstractC20193e = (AbstractC20193e) this.f25413b;
                abstractC20193e.f25422h = (w) obj;
                abstractC20193e.h(null);
                break;
            default:
                ((q) this.f25413b).b(PreviewView.StreamState.f25405c);
                break;
        }
        return null;
    }
}
