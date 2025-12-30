package androidx.compose.animation.core;

import kotlin.Metadata;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: InternalMutatorMutex.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\b\u0080\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/core/MutatePriority;", "", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class MutatePriority {

    /* renamed from: b, reason: collision with root package name */
    public static final MutatePriority f26109b;

    /* renamed from: c, reason: collision with root package name */
    public static final /* synthetic */ MutatePriority[] f26110c;

    /* renamed from: d, reason: collision with root package name */
    public static final /* synthetic */ kotlin.enums.a f26111d;

    static {
        MutatePriority mutatePriority = new MutatePriority("Default", 0);
        f26109b = mutatePriority;
        MutatePriority[] mutatePriorityArr = {mutatePriority, new MutatePriority("UserInput", 1), new MutatePriority("PreventUserInput", 2)};
        f26110c = mutatePriorityArr;
        f26111d = kotlin.enums.c.a(mutatePriorityArr);
    }

    public MutatePriority() {
        throw null;
    }

    public static MutatePriority valueOf(String str) {
        return (MutatePriority) Enum.valueOf(MutatePriority.class, str);
    }

    public static MutatePriority[] values() {
        return (MutatePriority[]) f26110c.clone();
    }
}
