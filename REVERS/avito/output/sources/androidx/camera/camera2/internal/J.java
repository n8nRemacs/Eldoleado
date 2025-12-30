package androidx.camera.camera2.internal;

import androidx.camera.camera2.internal.D;
import java.util.List;
import q.InterfaceC47174a;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class J implements InterfaceC47174a {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f22986a;

    @Override // q.InterfaceC47174a
    public final Object apply(Object obj) {
        switch (this.f22986a) {
            case 0:
                int i12 = D.f.f22966f;
                return Boolean.FALSE;
            case 1:
                return Boolean.TRUE;
            default:
                return Boolean.valueOf(((List) obj).contains(Boolean.TRUE));
        }
    }
}
