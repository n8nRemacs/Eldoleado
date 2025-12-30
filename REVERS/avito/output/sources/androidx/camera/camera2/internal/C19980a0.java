package androidx.camera.camera2.internal;

import androidx.camera.core.impl.AbstractC20109p;
import androidx.camera.core.impl.C20110q;
import java.util.ArrayList;

/* compiled from: CaptureCallbackConverter.java */
@j.X
/* renamed from: androidx.camera.camera2.internal.a0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C19980a0 {
    public static void a(AbstractC20109p abstractC20109p, ArrayList arrayList) {
        if (abstractC20109p instanceof C20110q.a) {
            ((C20110q.a) abstractC20109p).getClass();
            throw null;
        }
        if (abstractC20109p instanceof Z) {
            arrayList.add(((Z) abstractC20109p).f23093a);
        } else {
            arrayList.add(new Y(abstractC20109p));
        }
    }
}
