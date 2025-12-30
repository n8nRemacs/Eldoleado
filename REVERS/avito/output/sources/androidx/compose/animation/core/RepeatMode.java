package androidx.compose.animation.core;

import kotlin.Metadata;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: AnimationSpec.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/core/RepeatMode;", "", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class RepeatMode {

    /* renamed from: b, reason: collision with root package name */
    public static final RepeatMode f26133b;

    /* renamed from: c, reason: collision with root package name */
    public static final RepeatMode f26134c;

    /* renamed from: d, reason: collision with root package name */
    public static final /* synthetic */ RepeatMode[] f26135d;

    /* renamed from: e, reason: collision with root package name */
    public static final /* synthetic */ kotlin.enums.a f26136e;

    static {
        RepeatMode repeatMode = new RepeatMode("Restart", 0);
        f26133b = repeatMode;
        RepeatMode repeatMode2 = new RepeatMode("Reverse", 1);
        f26134c = repeatMode2;
        RepeatMode[] repeatModeArr = {repeatMode, repeatMode2};
        f26135d = repeatModeArr;
        f26136e = kotlin.enums.c.a(repeatModeArr);
    }

    public RepeatMode() {
        throw null;
    }

    public static RepeatMode valueOf(String str) {
        return (RepeatMode) Enum.valueOf(RepeatMode.class, str);
    }

    public static RepeatMode[] values() {
        return (RepeatMode[]) f26135d.clone();
    }
}
