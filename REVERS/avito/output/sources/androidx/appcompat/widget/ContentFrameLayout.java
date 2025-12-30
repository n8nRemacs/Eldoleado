package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;
import androidx.annotation.RestrictTo;

@RestrictTo
/* loaded from: classes.dex */
public class ContentFrameLayout extends FrameLayout {

    /* renamed from: b, reason: collision with root package name */
    public TypedValue f22105b;

    /* renamed from: c, reason: collision with root package name */
    public TypedValue f22106c;

    /* renamed from: d, reason: collision with root package name */
    public TypedValue f22107d;

    /* renamed from: e, reason: collision with root package name */
    public TypedValue f22108e;

    /* renamed from: f, reason: collision with root package name */
    public TypedValue f22109f;

    /* renamed from: g, reason: collision with root package name */
    public TypedValue f22110g;

    /* renamed from: h, reason: collision with root package name */
    public final Rect f22111h;

    /* renamed from: i, reason: collision with root package name */
    public a f22112i;

    public interface a {
        void onDetachedFromWindow();
    }

    public ContentFrameLayout(@j.N Context context, @j.P AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.f22111h = new Rect();
    }

    public TypedValue getFixedHeightMajor() {
        if (this.f22109f == null) {
            this.f22109f = new TypedValue();
        }
        return this.f22109f;
    }

    public TypedValue getFixedHeightMinor() {
        if (this.f22110g == null) {
            this.f22110g = new TypedValue();
        }
        return this.f22110g;
    }

    public TypedValue getFixedWidthMajor() {
        if (this.f22107d == null) {
            this.f22107d = new TypedValue();
        }
        return this.f22107d;
    }

    public TypedValue getFixedWidthMinor() {
        if (this.f22108e == null) {
            this.f22108e = new TypedValue();
        }
        return this.f22108e;
    }

    public TypedValue getMinWidthMajor() {
        if (this.f22105b == null) {
            this.f22105b = new TypedValue();
        }
        return this.f22105b;
    }

    public TypedValue getMinWidthMinor() {
        if (this.f22106c == null) {
            this.f22106c = new TypedValue();
        }
        return this.f22106c;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        a aVar = this.f22112i;
        if (aVar != null) {
            aVar.onDetachedFromWindow();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00de  */
    @Override // android.widget.FrameLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void onMeasure(int r17, int r18) {
        /*
            Method dump skipped, instructions count: 229
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ContentFrameLayout.onMeasure(int, int):void");
    }

    public void setAttachListener(a aVar) {
        this.f22112i = aVar;
    }
}
