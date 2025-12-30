package Ak;

import Y61.k;
import Y61.l;
import androidx.compose.foundation.H;
import androidx.compose.runtime.internal.P;
import com.avito.android.analytics.screens.mvi.q;
import com.avito.android.btob_business_trip.models.SuccessScreenInfo;
import com.avito.android.deep_linking.links.DeepLink;
import com.avito.android.remote.model.UniversalImage;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;
import kotlin.jvm.internal.L;

/* compiled from: B2bBusinessTripSuccessScreenState.kt */
@P
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u00002\u00020\u0001:\u0002\u0002\u0003¨\u0006\u0004"}, d2 = {"LAk/c;", "Lcom/avito/android/analytics/screens/mvi/q;", "a", "b", "_avito_btob-business-trip_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: Ak.c, reason: case insensitive filesystem */
/* loaded from: classes11.dex */
public final /* data */ class C13046c extends q {

    /* renamed from: d, reason: collision with root package name */
    @k
    public static final a f597d = new a(null);

    /* renamed from: e, reason: collision with root package name */
    @k
    public static final C13046c f598e = new C13046c(b.a.f601a, null);

    /* renamed from: b, reason: collision with root package name */
    @k
    public final b f599b;

    /* renamed from: c, reason: collision with root package name */
    @l
    public final SuccessScreenInfo f600c;

    /* compiled from: B2bBusinessTripSuccessScreenState.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0014\u0010\u0005\u001a\u00020\u00048\u0006X\u0086T¢\u0006\u0006\n\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048\u0006X\u0086T¢\u0006\u0006\n\u0004\b\u0007\u0010\u0006¨\u0006\b"}, d2 = {"LAk/c$a;", "", "<init>", "()V", "", "IMAGE_HEIGHT", "I", "IMAGE_WIDTH", "_avito_btob-business-trip_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: Ak.c$a */
    public static final class a {
        public /* synthetic */ a(C42822w c42822w) {
            this();
        }

        public a() {
        }
    }

    /* compiled from: B2bBusinessTripSuccessScreenState.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0002\u0002\u0003\u0082\u0001\u0002\u0004\u0005¨\u0006\u0006"}, d2 = {"LAk/c$b;", "", "a", "b", "LAk/c$b$a;", "LAk/c$b$b;", "_avito_btob-business-trip_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: Ak.c$b */
    public interface b {

        /* compiled from: B2bBusinessTripSuccessScreenState.kt */
        @P
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÇ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LAk/c$b$a;", "LAk/c$b;", "<init>", "()V", "_avito_btob-business-trip_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
        /* renamed from: Ak.c$b$a */
        public static final /* data */ class a implements b {

            /* renamed from: a, reason: collision with root package name */
            @k
            public static final a f601a = new a();

            public final boolean equals(@l Object obj) {
                return this == obj || (obj instanceof a);
            }

            public final int hashCode() {
                return -851672888;
            }

            @k
            public final String toString() {
                return "Init";
            }
        }

        /* compiled from: B2bBusinessTripSuccessScreenState.kt */
        @P
        @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAk/c$b$b;", "LAk/c$b;", "_avito_btob-business-trip_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
        /* renamed from: Ak.c$b$b, reason: collision with other inner class name */
        public static final /* data */ class C0025b implements b {

            /* renamed from: a, reason: collision with root package name */
            @k
            public final String f602a;

            /* renamed from: b, reason: collision with root package name */
            @k
            public final String f603b;

            /* renamed from: c, reason: collision with root package name */
            @k
            public final String f604c;

            /* renamed from: d, reason: collision with root package name */
            @k
            public final UniversalImage f605d;

            /* renamed from: e, reason: collision with root package name */
            @k
            public final DeepLink f606e;

            public C0025b(@k String str, @k DeepLink deepLink, @k UniversalImage universalImage, @k String str2, @k String str3) {
                this.f602a = str;
                this.f603b = str2;
                this.f604c = str3;
                this.f605d = universalImage;
                this.f606e = deepLink;
            }

            public final boolean equals(@l Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof C0025b)) {
                    return false;
                }
                C0025b c0025b = (C0025b) obj;
                return L.f(this.f602a, c0025b.f602a) && L.f(this.f603b, c0025b.f603b) && L.f(this.f604c, c0025b.f604c) && L.f(this.f605d, c0025b.f605d) && L.f(this.f606e, c0025b.f606e);
            }

            public final int hashCode() {
                return this.f606e.hashCode() + com.avito.android.actions_sheet.a.a(H.d(H.d(this.f602a.hashCode() * 31, 31, this.f603b), 31, this.f604c), 31, this.f605d);
            }

            @k
            public final String toString() {
                StringBuilder sb2 = new StringBuilder("SuccessScreen(header=");
                sb2.append(this.f602a);
                sb2.append(", subheader=");
                sb2.append(this.f603b);
                sb2.append(", buttonTitle=");
                sb2.append(this.f604c);
                sb2.append(", image=");
                sb2.append(this.f605d);
                sb2.append(", buttonDeepLink=");
                return com.avito.android.actions_sheet.a.v(sb2, this.f606e, ')');
            }
        }
    }

    public C13046c(@k b bVar, @l SuccessScreenInfo successScreenInfo) {
        this.f599b = bVar;
        this.f600c = successScreenInfo;
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C13046c)) {
            return false;
        }
        C13046c c13046c = (C13046c) obj;
        return L.f(this.f599b, c13046c.f599b) && L.f(this.f600c, c13046c.f600c);
    }

    public final int hashCode() {
        int iHashCode = this.f599b.hashCode() * 31;
        SuccessScreenInfo successScreenInfo = this.f600c;
        return iHashCode + (successScreenInfo == null ? 0 : successScreenInfo.hashCode());
    }

    @k
    public final String toString() {
        return "B2bBusinessTripSuccessScreenState(viewState=" + this.f599b + ", successScreenInfo=" + this.f600c + ')';
    }
}
