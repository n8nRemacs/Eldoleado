package aN;

import Y61.k;
import Y61.l;
import androidx.media3.exoplayer.analytics.m;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;
import kotlin.jvm.internal.L;

/* compiled from: ImvGoodsAdvertAction.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0005\u0002\u0003\u0004\u0005\u0006\u0082\u0001\u0005\u0007\b\t\n\u000b¨\u0006\f"}, d2 = {"LaN/a;", "", "a", "b", "c", "d", "e", "LaN/a$a;", "LaN/a$b;", "LaN/a$c;", "LaN/a$d;", "LaN/a$e;", "_avito_imv-goods-advert_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: aN.a, reason: case insensitive filesystem */
/* loaded from: classes14.dex */
public interface InterfaceC19793a {

    /* compiled from: ImvGoodsAdvertAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LaN/a$a;", "LaN/a;", "<init>", "()V", "_avito_imv-goods-advert_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aN.a$a, reason: collision with other inner class name */
    public static final class C1490a implements InterfaceC19793a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final C1490a f20874a = new C1490a();
    }

    /* compiled from: ImvGoodsAdvertAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LaN/a$c;", "LaN/a;", "<init>", "()V", "_avito_imv-goods-advert_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aN.a$c */
    public static final class c implements InterfaceC19793a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final c f20877a = new c();
    }

    /* compiled from: ImvGoodsAdvertAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LaN/a$d;", "LaN/a;", "<init>", "()V", "_avito_imv-goods-advert_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aN.a$d */
    public static final class d implements InterfaceC19793a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final d f20878a = new d();
    }

    /* compiled from: ImvGoodsAdvertAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LaN/a$b;", "LaN/a;", "_avito_imv-goods-advert_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aN.a$b */
    public static final /* data */ class b implements InterfaceC19793a {

        /* renamed from: a, reason: collision with root package name */
        public final int f20875a;

        /* renamed from: b, reason: collision with root package name */
        @l
        public final Long f20876b;

        public b(int i12, @l Long l12) {
            this.f20875a = i12;
            this.f20876b = l12;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof b)) {
                return false;
            }
            b bVar = (b) obj;
            return this.f20875a == bVar.f20875a && L.f(this.f20876b, bVar.f20876b);
        }

        public final int hashCode() {
            int iHashCode = Integer.hashCode(this.f20875a) * 31;
            Long l12 = this.f20876b;
            return iHashCode + (l12 == null ? 0 : l12.hashCode());
        }

        @k
        public final String toString() {
            StringBuilder sb2 = new StringBuilder("OpenGallery(position=");
            sb2.append(this.f20875a);
            sb2.append(", stateId=");
            return m.m(sb2, this.f20876b, ')');
        }

        public /* synthetic */ b(int i12, Long l12, int i13, C42822w c42822w) {
            this(i12, (i13 & 2) != 0 ? null : l12);
        }
    }

    /* compiled from: ImvGoodsAdvertAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LaN/a$e;", "LaN/a;", "_avito_imv-goods-advert_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aN.a$e */
    public static final /* data */ class e implements InterfaceC19793a {

        /* renamed from: a, reason: collision with root package name */
        public final int f20879a;

        /* renamed from: b, reason: collision with root package name */
        @l
        public final Long f20880b;

        public e(int i12, @l Long l12) {
            this.f20879a = i12;
            this.f20880b = l12;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof e)) {
                return false;
            }
            e eVar = (e) obj;
            return this.f20879a == eVar.f20879a && L.f(this.f20880b, eVar.f20880b);
        }

        public final int hashCode() {
            int iHashCode = Integer.hashCode(this.f20879a) * 31;
            Long l12 = this.f20880b;
            return iHashCode + (l12 == null ? 0 : l12.hashCode());
        }

        @k
        public final String toString() {
            StringBuilder sb2 = new StringBuilder("UpdateGalleryPosition(position=");
            sb2.append(this.f20879a);
            sb2.append(", stateId=");
            return m.m(sb2, this.f20880b, ')');
        }

        public /* synthetic */ e(int i12, Long l12, int i13, C42822w c42822w) {
            this(i12, (i13 & 2) != 0 ? null : l12);
        }
    }
}
