package androidx.compose.animation;

import java.util.Comparator;
import kotlin.Metadata;

/* compiled from: Comparisons.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\f\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\u0010\b\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u00002\u000e\u0010\u0002\u001a\n \u0001*\u0004\u0018\u00018\u00008\u00002\u000e\u0010\u0003\u001a\n \u0001*\u0004\u0018\u00018\u00008\u0000H\n¢\u0006\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"T", "kotlin.jvm.PlatformType", "a", "b", "", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I", "kotlin/comparisons/b", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* loaded from: classes.dex */
public final class R1<T> implements Comparator {
    @Override // java.util.Comparator
    public final int compare(T t12, T t13) {
        float zIndex;
        InterfaceC20388t1 interfaceC20388t1 = (InterfaceC20388t1) t12;
        float zIndex2 = -1.0f;
        if (interfaceC20388t1.getZIndex() == 0.0f && (interfaceC20388t1 instanceof M1)) {
            zIndex = -1.0f;
        } else {
            zIndex = interfaceC20388t1.getZIndex();
        }
        Float fValueOf = Float.valueOf(zIndex);
        InterfaceC20388t1 interfaceC20388t12 = (InterfaceC20388t1) t13;
        if (interfaceC20388t12.getZIndex() == 0.0f && (interfaceC20388t12 instanceof M1)) {
        } else {
            zIndex2 = interfaceC20388t12.getZIndex();
        }
        return kotlin.comparisons.a.b(fValueOf, Float.valueOf(zIndex2));
    }
}
