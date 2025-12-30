package androidx.camera.core.impl;

@j.X
/* loaded from: classes.dex */
public class CameraCaptureFailure {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static final class Reason {

        /* renamed from: b, reason: collision with root package name */
        public static final Reason f23875b;

        /* renamed from: c, reason: collision with root package name */
        public static final /* synthetic */ Reason[] f23876c;

        static {
            Reason reason = new Reason("ERROR", 0);
            f23875b = reason;
            f23876c = new Reason[]{reason};
        }

        public Reason() {
            throw null;
        }

        public static Reason valueOf(String str) {
            return (Reason) Enum.valueOf(Reason.class, str);
        }

        public static Reason[] values() {
            return (Reason[]) f23876c.clone();
        }
    }
}
