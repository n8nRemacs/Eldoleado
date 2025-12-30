package A0;

import X41.g;
import Y61.k;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;
import kotlin.jvm.internal.L;

/* compiled from: AnimatedVisibilityState.android.kt */
@g
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0081@\u0018\u00002\u00020\u0001:\u0001\u0002\u0088\u0001\u0003\u0092\u0001\u00020\u0004¨\u0006\u0005"}, d2 = {"LA0/a;", "LA0/b;", "a", "value", "", "ui-tooling_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class a implements b {

    /* renamed from: b, reason: collision with root package name */
    @k
    public static final C0000a f29b = new C0000a(null);

    /* renamed from: c, reason: collision with root package name */
    @k
    public static final String f30c = "Enter";

    /* renamed from: d, reason: collision with root package name */
    @k
    public static final String f31d = "Exit";

    /* renamed from: a, reason: collision with root package name */
    @k
    public final String f32a;

    /* compiled from: AnimatedVisibilityState.android.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LA0/a$a;", "", "<init>", "()V", "ui-tooling_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: A0.a$a, reason: collision with other inner class name */
    public static final class C0000a {
        public /* synthetic */ C0000a(C42822w c42822w) {
            this();
        }

        @k
        public static String a() {
            return a.f30c;
        }

        @k
        public static String b() {
            return a.f31d;
        }

        public C0000a() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ a(String str) {
        this.f32a = str;
    }

    public static final /* synthetic */ a a(String str) {
        return new a(str);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof a) {
            return L.f(this.f32a, ((a) obj).f32a);
        }
        return false;
    }

    public final int hashCode() {
        return this.f32a.hashCode();
    }

    @k
    /* renamed from: toString, reason: from getter */
    public final String getF32a() {
        return this.f32a;
    }
}
