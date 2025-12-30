package Ah;

import Y61.k;
import Y61.l;
import androidx.compose.runtime.internal.P;
import com.akita.compose.component.accordion.s;
import com.avito.android.deep_linking.links.DeepLink;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: BblConfigureV2OneTimeEvent.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bp\u0018\u00002\u00020\u0001:\u0002\u0002\u0003\u0082\u0001\u0002\u0004\u0005¨\u0006\u0006"}, d2 = {"LAh/b;", "", "a", "b", "LAh/b$a;", "LAh/b$b;", "_avito_bbl_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: Ah.b, reason: case insensitive filesystem */
/* loaded from: classes11.dex */
public interface InterfaceC13033b {

    /* compiled from: BblConfigureV2OneTimeEvent.kt */
    @P
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÇ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LAh/b$a;", "LAh/b;", "<init>", "()V", "_avito_bbl_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: Ah.b$a */
    public static final /* data */ class a implements InterfaceC13033b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final a f566a = new a();

        public final boolean equals(@l Object obj) {
            return this == obj || (obj instanceof a);
        }

        public final int hashCode() {
            return 372536017;
        }

        @k
        public final String toString() {
            return "Back";
        }
    }

    /* compiled from: BblConfigureV2OneTimeEvent.kt */
    @P
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAh/b$b;", "LAh/b;", "_avito_bbl_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: Ah.b$b, reason: collision with other inner class name */
    public static final /* data */ class C0022b implements InterfaceC13033b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final DeepLink f567a;

        /* renamed from: b, reason: collision with root package name */
        @l
        public final Long f568b;

        /* renamed from: c, reason: collision with root package name */
        @l
        public final Integer f569c;

        public C0022b(@k DeepLink deepLink, @l Integer num, @l Long l12) {
            this.f567a = deepLink;
            this.f568b = l12;
            this.f569c = num;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof C0022b)) {
                return false;
            }
            C0022b c0022b = (C0022b) obj;
            return L.f(this.f567a, c0022b.f567a) && L.f(this.f568b, c0022b.f568b) && L.f(this.f569c, c0022b.f569c);
        }

        public final int hashCode() {
            int iHashCode = this.f567a.hashCode() * 31;
            Long l12 = this.f568b;
            int iHashCode2 = (iHashCode + (l12 == null ? 0 : l12.hashCode())) * 31;
            Integer num = this.f569c;
            return iHashCode2 + (num != null ? num.hashCode() : 0);
        }

        @k
        public final String toString() {
            StringBuilder sb2 = new StringBuilder("HandleDeeplink(deepLink=");
            sb2.append(this.f567a);
            sb2.append(", configId=");
            sb2.append(this.f568b);
            sb2.append(", buttonId=");
            return s.j(sb2, this.f569c, ')');
        }
    }
}
