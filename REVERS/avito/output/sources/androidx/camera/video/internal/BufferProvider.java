package androidx.camera.video.internal;

import androidx.camera.core.impl.q0;
import com.google.common.util.concurrent.D0;
import j.N;
import j.X;

@X
/* loaded from: classes.dex */
public interface BufferProvider<T> extends q0<State> {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static final class State {

        /* renamed from: b, reason: collision with root package name */
        public static final State f24942b;

        /* renamed from: c, reason: collision with root package name */
        public static final State f24943c;

        /* renamed from: d, reason: collision with root package name */
        public static final /* synthetic */ State[] f24944d;

        static {
            State state = new State("ACTIVE", 0);
            f24942b = state;
            State state2 = new State("INACTIVE", 1);
            f24943c = state2;
            f24944d = new State[]{state, state2};
        }

        public State() {
            throw null;
        }

        public static State valueOf(String str) {
            return (State) Enum.valueOf(State.class, str);
        }

        public static State[] values() {
            return (State[]) f24944d.clone();
        }
    }

    @N
    D0<T> e();
}
