package androidx.compose.animation.core;

import androidx.compose.animation.core.AbstractC20330x;
import kotlin.Metadata;

/* compiled from: Animation.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\bf\u0018\u0000*\u0004\b\u0000\u0010\u0001*\b\b\u0001\u0010\u0003*\u00020\u00022\u00020\u0004ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0005À\u0006\u0003"}, d2 = {"Landroidx/compose/animation/core/i;", "T", "Landroidx/compose/animation/core/x;", "V", "", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.i, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public interface InterfaceC20286i<T, V extends AbstractC20330x> {

    /* compiled from: Animation.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.compose.animation.core.i$a */
    public static final class a {
    }

    boolean a();

    long b();

    default boolean c(long j12) {
        return j12 >= b();
    }

    @Y61.k
    H1<T, V> d();

    T e(long j12);

    T f();

    @Y61.k
    V g(long j12);
}
