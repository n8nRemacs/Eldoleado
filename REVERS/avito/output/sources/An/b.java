package aN;

import Y61.k;
import Y61.l;
import androidx.appcompat.app.r;
import androidx.media3.exoplayer.analytics.m;
import com.avito.android.remote.model.AdvertDetails;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;
import kotlin.jvm.internal.L;

/* compiled from: ImvGoodsAdvertOneTimeEvent.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0002\u0002\u0003\u0082\u0001\u0002\u0004\u0005¨\u0006\u0006"}, d2 = {"LaN/b;", "", "a", "b", "LaN/b$a;", "LaN/b$b;", "_avito_imv-goods-advert_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes14.dex */
public interface b {

    /* compiled from: ImvGoodsAdvertOneTimeEvent.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LaN/b$a;", "LaN/b;", "<init>", "()V", "_avito_imv-goods-advert_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class a implements b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final a f20881a = new a();

        public final boolean equals(@l Object obj) {
            return this == obj || (obj instanceof a);
        }

        public final int hashCode() {
            return -1245127015;
        }

        @k
        public final String toString() {
            return "CloseScreen";
        }
    }

    /* compiled from: ImvGoodsAdvertOneTimeEvent.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LaN/b$b;", "LaN/b;", "_avito_imv-goods-advert_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aN.b$b, reason: collision with other inner class name */
    public static final /* data */ class C1491b implements b {

        /* renamed from: a, reason: collision with root package name */
        @l
        public final AdvertDetails f20882a;

        /* renamed from: b, reason: collision with root package name */
        public final int f20883b;

        /* renamed from: c, reason: collision with root package name */
        @l
        public final Long f20884c;

        public C1491b(@l AdvertDetails advertDetails, int i12, @l Long l12) {
            this.f20882a = advertDetails;
            this.f20883b = i12;
            this.f20884c = l12;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof C1491b)) {
                return false;
            }
            C1491b c1491b = (C1491b) obj;
            return L.f(this.f20882a, c1491b.f20882a) && this.f20883b == c1491b.f20883b && L.f(this.f20884c, c1491b.f20884c);
        }

        public final int hashCode() {
            AdvertDetails advertDetails = this.f20882a;
            int iE2 = r.e(this.f20883b, (advertDetails == null ? 0 : advertDetails.hashCode()) * 31, 31);
            Long l12 = this.f20884c;
            return iE2 + (l12 != null ? l12.hashCode() : 0);
        }

        @k
        public final String toString() {
            StringBuilder sb2 = new StringBuilder("OpenGallery(advertDetails=");
            sb2.append(this.f20882a);
            sb2.append(", position=");
            sb2.append(this.f20883b);
            sb2.append(", stateId=");
            return m.m(sb2, this.f20884c, ')');
        }

        public /* synthetic */ C1491b(AdvertDetails advertDetails, int i12, Long l12, int i13, C42822w c42822w) {
            this(advertDetails, i12, (i13 & 4) != 0 ? null : l12);
        }
    }
}
