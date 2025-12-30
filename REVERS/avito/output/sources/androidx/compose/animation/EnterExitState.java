package androidx.compose.animation;

import kotlin.Metadata;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: AnimatedVisibility.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/EnterExitState;", "", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class EnterExitState {

    /* renamed from: b, reason: collision with root package name */
    public static final EnterExitState f25872b;

    /* renamed from: c, reason: collision with root package name */
    public static final EnterExitState f25873c;

    /* renamed from: d, reason: collision with root package name */
    public static final EnterExitState f25874d;

    /* renamed from: e, reason: collision with root package name */
    public static final /* synthetic */ EnterExitState[] f25875e;

    /* renamed from: f, reason: collision with root package name */
    public static final /* synthetic */ kotlin.enums.a f25876f;

    static {
        EnterExitState enterExitState = new EnterExitState("PreEnter", 0);
        f25872b = enterExitState;
        EnterExitState enterExitState2 = new EnterExitState("Visible", 1);
        f25873c = enterExitState2;
        EnterExitState enterExitState3 = new EnterExitState("PostExit", 2);
        f25874d = enterExitState3;
        EnterExitState[] enterExitStateArr = {enterExitState, enterExitState2, enterExitState3};
        f25875e = enterExitStateArr;
        f25876f = kotlin.enums.c.a(enterExitStateArr);
    }

    public EnterExitState() {
        throw null;
    }

    public static EnterExitState valueOf(String str) {
        return (EnterExitState) Enum.valueOf(EnterExitState.class, str);
    }

    public static EnterExitState[] values() {
        return (EnterExitState[]) f25875e.clone();
    }
}
