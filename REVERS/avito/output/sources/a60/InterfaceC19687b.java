package a60;

import Y61.k;
import Y61.l;
import androidx.compose.runtime.internal.P;
import com.avito.android.deep_linking.links.DeepLink;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: CheckMergePossibilityOneTimeEvent.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bp\u0018\u00002\u00020\u0001:\u0002\u0002\u0003\u0082\u0001\u0002\u0004\u0005¨\u0006\u0006"}, d2 = {"La60/b;", "", "a", "b", "La60/b$a;", "La60/b$b;", "_avito_passport_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a60.b, reason: case insensitive filesystem */
/* loaded from: classes15.dex */
public interface InterfaceC19687b {

    /* compiled from: CheckMergePossibilityOneTimeEvent.kt */
    @P
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÇ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"La60/b$a;", "La60/b;", "<init>", "()V", "_avito_passport_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: a60.b$a */
    public static final class a implements InterfaceC19687b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final a f20742a = new a();
    }

    /* compiled from: CheckMergePossibilityOneTimeEvent.kt */
    @P
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"La60/b$b;", "La60/b;", "_avito_passport_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: a60.b$b, reason: collision with other inner class name */
    public static final /* data */ class C1483b implements InterfaceC19687b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final DeepLink f20743a;

        public C1483b(@k DeepLink deepLink) {
            this.f20743a = deepLink;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof C1483b) && L.f(this.f20743a, ((C1483b) obj).f20743a);
        }

        public final int hashCode() {
            return this.f20743a.hashCode();
        }

        @k
        public final String toString() {
            return com.avito.android.actions_sheet.a.v(new StringBuilder("ShowDeeplinkContent(deepLink="), this.f20743a, ')');
        }
    }
}
