package androidx.compose.animation.core;

import androidx.compose.animation.core.AbstractC20330x;
import kotlin.Metadata;

/* compiled from: Animatable.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u0001*\b\b\u0001\u0010\u0003*\u00020\u00022\u00020\u0004¨\u0006\u0005"}, d2 = {"Landroidx/compose/animation/core/n;", "T", "Landroidx/compose/animation/core/x;", "V", "", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.n, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20301n<T, V extends AbstractC20330x> {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final r<T, V> f26381a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final AnimationEndReason f26382b;

    public C20301n(@Y61.k r<T, V> rVar, @Y61.k AnimationEndReason animationEndReason) {
        this.f26381a = rVar;
        this.f26382b = animationEndReason;
    }

    @Y61.k
    public final String toString() {
        return "AnimationResult(endReason=" + this.f26382b + ", endState=" + this.f26381a + ')';
    }
}
