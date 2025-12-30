package androidx.camera.video;

import androidx.annotation.RestrictTo;
import androidx.camera.core.InterfaceC20142t;
import androidx.camera.core.K0;
import androidx.camera.core.impl.Timebase;
import androidx.camera.core.impl.q0;

@j.X
/* loaded from: classes.dex */
public interface VideoOutput {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    @RestrictTo
    public static final class SourceState {

        /* renamed from: b, reason: collision with root package name */
        public static final SourceState f24837b;

        /* renamed from: c, reason: collision with root package name */
        public static final SourceState f24838c;

        /* renamed from: d, reason: collision with root package name */
        public static final SourceState f24839d;

        /* renamed from: e, reason: collision with root package name */
        public static final /* synthetic */ SourceState[] f24840e;

        static {
            SourceState sourceState = new SourceState("ACTIVE_STREAMING", 0);
            f24837b = sourceState;
            SourceState sourceState2 = new SourceState("ACTIVE_NON_STREAMING", 1);
            f24838c = sourceState2;
            SourceState sourceState3 = new SourceState("INACTIVE", 2);
            f24839d = sourceState3;
            f24840e = new SourceState[]{sourceState, sourceState2, sourceState3};
        }

        public SourceState() {
            throw null;
        }

        public static SourceState valueOf(String str) {
            return (SourceState) Enum.valueOf(SourceState.class, str);
        }

        public static SourceState[] values() {
            return (SourceState[]) f24840e.clone();
        }
    }

    void a(@j.N K0 k02);

    @RestrictTo
    default void b(@j.N K0 k02, @j.N Timebase timebase) {
        a(k02);
    }

    @j.N
    @RestrictTo
    default q0<AbstractC20184v> c() {
        return androidx.camera.core.impl.K.f23988b;
    }

    @j.N
    @RestrictTo
    default q0<W> d() {
        return W.f24843c;
    }

    @j.N
    @RestrictTo
    default X f(@j.N InterfaceC20142t interfaceC20142t) {
        return X.f24847a;
    }

    @RestrictTo
    default void e(@j.N SourceState sourceState) {
    }
}
