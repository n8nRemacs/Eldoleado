package a20;

import Y10.b;
import Y61.k;
import Y61.l;
import androidx.appcompat.app.r;
import androidx.compose.foundation.H;
import androidx.compose.runtime.internal.P;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.C42745f0;
import kotlin.jvm.internal.L;
import kotlin.jvm.internal.s0;

/* compiled from: ApplicationContactInfoForm.kt */
@P
@s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"La20/a;", "", "_avito_mortgage-invite_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a20.a, reason: case insensitive filesystem */
/* loaded from: classes15.dex */
public final /* data */ class C19669a {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final String f20709a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final String f20710b;

    /* renamed from: c, reason: collision with root package name */
    @k
    public final String f20711c;

    /* renamed from: d, reason: collision with root package name */
    @k
    public final String f20712d;

    /* renamed from: e, reason: collision with root package name */
    public final boolean f20713e;

    /* renamed from: f, reason: collision with root package name */
    @k
    public final Y10.a f20714f;

    /* renamed from: g, reason: collision with root package name */
    @k
    public final Y10.a f20715g;

    /* renamed from: h, reason: collision with root package name */
    public final boolean f20716h;

    public C19669a(@k String str, @k String str2, @k String str3, @k String str4, boolean z12, @k Y10.a aVar, @k Y10.a aVar2) {
        this.f20709a = str;
        this.f20710b = str2;
        this.f20711c = str3;
        this.f20712d = str4;
        this.f20713e = z12;
        this.f20714f = aVar;
        this.f20715g = aVar2;
        ArrayList arrayListH0 = C42745f0.h0(aVar2.f19259b, aVar.f19259b);
        boolean z13 = true;
        if (!arrayListH0.isEmpty()) {
            Iterator it = arrayListH0.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                b bVar = (b) it.next();
                if (bVar.f19266f && bVar.f19269i.length() <= 0) {
                    z13 = false;
                    break;
                }
            }
        }
        this.f20716h = z13;
    }

    public static C19669a a(C19669a c19669a, boolean z12, Y10.a aVar, Y10.a aVar2, int i12) {
        String str = c19669a.f20709a;
        String str2 = c19669a.f20710b;
        String str3 = c19669a.f20711c;
        String str4 = c19669a.f20712d;
        if ((i12 & 16) != 0) {
            z12 = c19669a.f20713e;
        }
        boolean z13 = z12;
        if ((i12 & 32) != 0) {
            aVar = c19669a.f20714f;
        }
        Y10.a aVar3 = aVar;
        if ((i12 & 64) != 0) {
            aVar2 = c19669a.f20715g;
        }
        c19669a.getClass();
        return new C19669a(str, str2, str3, str4, z13, aVar3, aVar2);
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C19669a)) {
            return false;
        }
        C19669a c19669a = (C19669a) obj;
        return L.f(this.f20709a, c19669a.f20709a) && L.f(this.f20710b, c19669a.f20710b) && L.f(this.f20711c, c19669a.f20711c) && L.f(this.f20712d, c19669a.f20712d) && this.f20713e == c19669a.f20713e && L.f(this.f20714f, c19669a.f20714f) && L.f(this.f20715g, c19669a.f20715g);
    }

    public final int hashCode() {
        return this.f20715g.hashCode() + ((this.f20714f.hashCode() + r.i(H.d(H.d(H.d(this.f20709a.hashCode() * 31, 31, this.f20710b), 31, this.f20711c), 31, this.f20712d), 31, this.f20713e)) * 31);
    }

    @k
    public final String toString() {
        return "ApplicationContactInfoForm(screenTitle=" + this.f20709a + ", title=" + this.f20710b + ", subtitle=" + this.f20711c + ", actionTitle=" + this.f20712d + ", isActionLoading=" + this.f20713e + ", clientBlock=" + this.f20714f + ", agentBlock=" + this.f20715g + ')';
    }
}
