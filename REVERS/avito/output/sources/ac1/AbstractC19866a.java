package ac1;

import Y61.k;
import androidx.compose.runtime.internal.P;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

@P
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002:\u0002\u0003\u0004\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lac1/a;", "T", "", "a", "b", "Lac1/a$a;", "Lac1/a$b;", "SberIdSDK_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: ac1.a, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public abstract class AbstractC19866a<T> {

    /* renamed from: a, reason: collision with root package name */
    public final int f21077a;

    @P
    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001¨\u0006\u0003"}, d2 = {"Lac1/a$a;", "Lac1/a;", "", "SberIdSDK_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: ac1.a$a, reason: collision with other inner class name */
    public static final class C1499a extends AbstractC19866a {

        /* renamed from: b, reason: collision with root package name */
        public final int f21078b;

        /* renamed from: c, reason: collision with root package name */
        @k
        public final String f21079c;

        public C1499a(int i12, @k String str) {
            super(i12, null);
            this.f21078b = i12;
            this.f21079c = str;
        }

        @Override // ac1.AbstractC19866a
        /* renamed from: a, reason: from getter */
        public final int getF21077a() {
            return this.f21078b;
        }
    }

    @P
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u00028\u00010\u0002¨\u0006\u0003"}, d2 = {"Lac1/a$b;", "T", "Lac1/a;", "SberIdSDK_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: ac1.a$b */
    public static final class b<T> extends AbstractC19866a<T> {

        /* renamed from: b, reason: collision with root package name */
        public final int f21080b;

        /* renamed from: c, reason: collision with root package name */
        public final T f21081c;

        public b(int i12, T t12) {
            super(i12, null);
            this.f21080b = i12;
            this.f21081c = t12;
        }

        @Override // ac1.AbstractC19866a
        /* renamed from: a, reason: from getter */
        public final int getF21077a() {
            return this.f21080b;
        }
    }

    public AbstractC19866a(int i12, C42822w c42822w) {
        this.f21077a = i12;
    }

    /* renamed from: a, reason: from getter */
    public int getF21077a() {
        return this.f21077a;
    }
}
