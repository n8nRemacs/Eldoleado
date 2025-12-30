package androidx.compose.animation.core;

import androidx.compose.animation.core.AbstractC20330x;
import kotlin.Metadata;

/* compiled from: VectorizedAnimationSpec.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\bf\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0004À\u0006\u0003"}, d2 = {"Landroidx/compose/animation/core/K1;", "Landroidx/compose/animation/core/x;", "V", "", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public interface K1<V extends AbstractC20330x> {

    /* compiled from: VectorizedAnimationSpec.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class a {
    }

    boolean a();

    long b(@Y61.k V v12, @Y61.k V v13, @Y61.k V v14);

    @Y61.k
    V c(long j12, @Y61.k V v12, @Y61.k V v13, @Y61.k V v14);

    @Y61.k
    default V d(@Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        return (V) c(b(v12, v13, v14), v12, v13, v14);
    }

    @Y61.k
    V e(long j12, @Y61.k V v12, @Y61.k V v13, @Y61.k V v14);
}
