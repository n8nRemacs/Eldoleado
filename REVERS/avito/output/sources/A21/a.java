package A21;

import Y61.k;
import Y61.l;
import android.graphics.Paint;
import android.text.Spanned;
import android.text.style.LineHeightSpan;
import j.U;
import kotlin.Metadata;
import kotlin.text.C43066x;

/* compiled from: LineHeightWithTopOffsetSpan.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LA21/a;", "Landroid/text/style/LineHeightSpan;", "div_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes7.dex */
public final class a implements LineHeightSpan {

    /* renamed from: b, reason: collision with root package name */
    @U
    public final int f58b;

    /* renamed from: c, reason: collision with root package name */
    @U
    public final int f59c;

    /* renamed from: d, reason: collision with root package name */
    public boolean f60d;

    /* renamed from: e, reason: collision with root package name */
    public int f61e = -1;

    /* renamed from: f, reason: collision with root package name */
    public int f62f = -1;

    /* renamed from: g, reason: collision with root package name */
    public int f63g = -1;

    public a(int i12, int i13) {
        this.f58b = i12;
        this.f59c = i13;
    }

    @Override // android.text.style.LineHeightSpan
    public final void chooseHeight(@l CharSequence charSequence, int i12, int i13, int i14, int i15, @k Paint.FontMetricsInt fontMetricsInt) {
        int i16;
        int i17;
        int i18;
        int i19;
        Spanned spanned = charSequence instanceof Spanned ? (Spanned) charSequence : null;
        if (spanned == null) {
            return;
        }
        int spanStart = spanned.getSpanStart(this);
        int spanEnd = spanned.getSpanEnd(this);
        if (this.f60d) {
            fontMetricsInt.ascent = this.f61e;
            fontMetricsInt.descent = this.f62f;
            fontMetricsInt.top = this.f63g;
        } else if (i12 >= spanStart) {
            this.f60d = true;
            this.f61e = fontMetricsInt.ascent;
            this.f62f = fontMetricsInt.descent;
            this.f63g = fontMetricsInt.top;
        }
        if (i12 >= spanStart && i13 <= spanEnd && (i17 = this.f59c) > 0 && (i19 = (i18 = fontMetricsInt.descent) - fontMetricsInt.ascent) >= 0) {
            int iB2 = kotlin.math.b.b(i18 * ((i17 * 1.0f) / i19));
            fontMetricsInt.descent = iB2;
            fontMetricsInt.ascent = iB2 - i17;
        }
        if (i12 <= spanStart && spanStart <= i13 && (i16 = this.f58b) > 0) {
            fontMetricsInt.ascent -= i16;
            fontMetricsInt.top -= i16;
        }
        if (C43066x.q(charSequence.subSequence(i12, i13).toString(), "\n", false)) {
            this.f60d = false;
        }
    }
}
