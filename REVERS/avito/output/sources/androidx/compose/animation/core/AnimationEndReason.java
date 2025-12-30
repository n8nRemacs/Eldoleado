package androidx.compose.animation.core;

import kotlin.Metadata;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: AnimationEndReason.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/core/AnimationEndReason;", "", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class AnimationEndReason {

    /* renamed from: b, reason: collision with root package name */
    public static final AnimationEndReason f26014b;

    /* renamed from: c, reason: collision with root package name */
    public static final AnimationEndReason f26015c;

    /* renamed from: d, reason: collision with root package name */
    public static final /* synthetic */ AnimationEndReason[] f26016d;

    /* renamed from: e, reason: collision with root package name */
    public static final /* synthetic */ kotlin.enums.a f26017e;

    static {
        AnimationEndReason animationEndReason = new AnimationEndReason("BoundReached", 0);
        f26014b = animationEndReason;
        AnimationEndReason animationEndReason2 = new AnimationEndReason("Finished", 1);
        f26015c = animationEndReason2;
        AnimationEndReason[] animationEndReasonArr = {animationEndReason, animationEndReason2};
        f26016d = animationEndReasonArr;
        f26017e = kotlin.enums.c.a(animationEndReasonArr);
    }

    public AnimationEndReason() {
        throw null;
    }

    public static AnimationEndReason valueOf(String str) {
        return (AnimationEndReason) Enum.valueOf(AnimationEndReason.class, str);
    }

    public static AnimationEndReason[] values() {
        return (AnimationEndReason[]) f26016d.clone();
    }
}
