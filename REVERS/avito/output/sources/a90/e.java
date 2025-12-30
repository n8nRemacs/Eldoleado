package a90;

import Y61.k;
import Y61.l;
import androidx.compose.foundation.H;
import androidx.compose.runtime.internal.P;
import com.avito.android.analytics.screens.mvi.r;
import com.avito.android.position_in_search.stats.screen.old_position_in_search.view.renderer.toast.ToastState;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;
import kotlin.jvm.internal.L;

/* compiled from: ScreenState.kt */
@P
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0080\b\u0018\u00002\u00020\u0001:\u0001\u0002¨\u0006\u0003"}, d2 = {"La90/e;", "", "a", "_avito_position-in-search_stats_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes15.dex */
public final /* data */ class e {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final com.avito.android.position_in_search.stats.screen.old_position_in_search.view.widget.toolbar.b f20769a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final a f20770b;

    /* renamed from: c, reason: collision with root package name */
    @k
    public final List<com.avito.android.position_in_search.stats.screen.old_position_in_search.view.items.a> f20771c;

    /* renamed from: d, reason: collision with root package name */
    @k
    public final ToastState f20772d;

    /* renamed from: e, reason: collision with root package name */
    @l
    public final r f20773e;

    /* renamed from: f, reason: collision with root package name */
    @l
    public final r f20774f;

    /* compiled from: ScreenState.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0002\u0002\u0003\u0082\u0001\u0002\u0004\u0005¨\u0006\u0006"}, d2 = {"La90/e$a;", "", "a", "b", "La90/e$a$a;", "La90/e$a$b;", "_avito_position-in-search_stats_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface a {

        /* compiled from: ScreenState.kt */
        @P
        @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"La90/e$a$a;", "La90/e$a;", "_avito_position-in-search_stats_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
        /* renamed from: a90.e$a$a, reason: collision with other inner class name */
        public static final /* data */ class C1484a implements a {

            /* renamed from: a, reason: collision with root package name */
            @k
            public final com.avito.android.position_in_search.stats.screen.old_position_in_search.view.widget.bottom_bar.e f20775a;

            public C1484a(@k com.avito.android.position_in_search.stats.screen.old_position_in_search.view.widget.bottom_bar.e eVar) {
                this.f20775a = eVar;
            }

            public final boolean equals(@l Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof C1484a) && L.f(this.f20775a, ((C1484a) obj).f20775a);
            }

            public final int hashCode() {
                return this.f20775a.hashCode();
            }

            @k
            public final String toString() {
                return "Content(state=" + this.f20775a + ')';
            }
        }

        /* compiled from: ScreenState.kt */
        @P
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÇ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"La90/e$a$b;", "La90/e$a;", "<init>", "()V", "_avito_position-in-search_stats_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class b implements a {

            /* renamed from: a, reason: collision with root package name */
            @k
            public static final b f20776a = new b();

            public final boolean equals(@l Object obj) {
                return this == obj || (obj instanceof b);
            }

            public final int hashCode() {
                return -2001021558;
            }

            @k
            public final String toString() {
                return "Empty";
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public e(@k com.avito.android.position_in_search.stats.screen.old_position_in_search.view.widget.toolbar.b bVar, @k a aVar, @k List<? extends com.avito.android.position_in_search.stats.screen.old_position_in_search.view.items.a> list, @k ToastState toastState, @l r rVar, @l r rVar2) {
        this.f20769a = bVar;
        this.f20770b = aVar;
        this.f20771c = list;
        this.f20772d = toastState;
        this.f20773e = rVar;
        this.f20774f = rVar2;
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof e)) {
            return false;
        }
        e eVar = (e) obj;
        return L.f(this.f20769a, eVar.f20769a) && L.f(this.f20770b, eVar.f20770b) && L.f(this.f20771c, eVar.f20771c) && L.f(this.f20772d, eVar.f20772d) && L.f(this.f20773e, eVar.f20773e) && L.f(this.f20774f, eVar.f20774f);
    }

    public final int hashCode() {
        int iHashCode = (this.f20772d.hashCode() + H.e((this.f20770b.hashCode() + (this.f20769a.hashCode() * 31)) * 31, 31, this.f20771c)) * 31;
        r rVar = this.f20773e;
        int iHashCode2 = (iHashCode + (rVar == null ? 0 : rVar.hashCode())) * 31;
        r rVar2 = this.f20774f;
        return iHashCode2 + (rVar2 != null ? rVar2.hashCode() : 0);
    }

    @k
    public final String toString() {
        return "ScreenState(toolbarState=" + this.f20769a + ", bottomState=" + this.f20770b + ", items=" + this.f20771c + ", toast=" + this.f20772d + ", positionInfoTrackerParams=" + this.f20773e + ", periodInfoTrackerParams=" + this.f20774f + ')';
    }

    public /* synthetic */ e(com.avito.android.position_in_search.stats.screen.old_position_in_search.view.widget.toolbar.b bVar, a aVar, List list, ToastState toastState, r rVar, r rVar2, int i12, C42822w c42822w) {
        this(bVar, aVar, list, (i12 & 8) != 0 ? ToastState.b.f221204a : toastState, rVar, rVar2);
    }
}
