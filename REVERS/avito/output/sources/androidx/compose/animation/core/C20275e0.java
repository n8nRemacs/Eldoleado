package androidx.compose.animation.core;

import androidx.compose.ui.platform.InterfaceC22512p1;
import kotlin.Metadata;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: InfiniteAnimationPolicy.kt */
@Metadata(d1 = {"\u0000\u0002\n\u0000¨\u0006\u0000"}, d2 = {"animation-core_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.e0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20275e0 {
    @Y61.l
    public static final Object a(@Y61.k Y41.l lVar, @Y61.k ContinuationImpl continuationImpl) {
        InterfaceC22512p1 interfaceC22512p1 = (InterfaceC22512p1) continuationImpl.getF411960f().get(InterfaceC22512p1.f41525E1);
        if (interfaceC22512p1 == null) {
            return androidx.compose.runtime.X0.a(continuationImpl.getF411960f()).N(lVar, continuationImpl);
        }
        new C20272d0(lVar, null);
        return interfaceC22512p1.u();
    }
}
