package androidx.camera.core;

@VY0.c
@j.X
/* loaded from: classes.dex */
public abstract class CameraState {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static final class ErrorType {

        /* renamed from: b, reason: collision with root package name */
        public static final /* synthetic */ ErrorType[] f23550b = {new ErrorType("RECOVERABLE", 0), new ErrorType("CRITICAL", 1)};

        /* JADX INFO: Fake field, exist only in values array */
        ErrorType EF5;

        public ErrorType() {
            throw null;
        }

        public static ErrorType valueOf(String str) {
            return (ErrorType) Enum.valueOf(ErrorType.class, str);
        }

        public static ErrorType[] values() {
            return (ErrorType[]) f23550b.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static final class Type {

        /* renamed from: b, reason: collision with root package name */
        public static final Type f23551b;

        /* renamed from: c, reason: collision with root package name */
        public static final Type f23552c;

        /* renamed from: d, reason: collision with root package name */
        public static final Type f23553d;

        /* renamed from: e, reason: collision with root package name */
        public static final Type f23554e;

        /* renamed from: f, reason: collision with root package name */
        public static final Type f23555f;

        /* renamed from: g, reason: collision with root package name */
        public static final /* synthetic */ Type[] f23556g;

        static {
            Type type = new Type("PENDING_OPEN", 0);
            f23551b = type;
            Type type2 = new Type("OPENING", 1);
            f23552c = type2;
            Type type3 = new Type("OPEN", 2);
            f23553d = type3;
            Type type4 = new Type("CLOSING", 3);
            f23554e = type4;
            Type type5 = new Type("CLOSED", 4);
            f23555f = type5;
            f23556g = new Type[]{type, type2, type3, type4, type5};
        }

        public Type() {
            throw null;
        }

        public static Type valueOf(String str) {
            return (Type) Enum.valueOf(Type.class, str);
        }

        public static Type[] values() {
            return (Type[]) f23556g.clone();
        }
    }

    @VY0.c
    public static abstract class a {
        @j.N
        public static a a(int i12) {
            return new C20059g(i12, null);
        }

        @j.N
        public static a b(int i12, @j.P Throwable th2) {
            return new C20059g(i12, th2);
        }

        @j.P
        public abstract Throwable c();

        public abstract int d();
    }

    @j.N
    public static CameraState a(@j.N Type type) {
        return new C20057f(type, null);
    }

    @j.N
    public static CameraState b(@j.N Type type, @j.P a aVar) {
        return new C20057f(type, aVar);
    }

    @j.P
    public abstract a c();

    @j.N
    public abstract Type d();
}
