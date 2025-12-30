package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.annotation.RestrictTo;
import androidx.appcompat.widget.Y;

@RestrictTo
/* loaded from: classes.dex */
public class FitWindowsFrameLayout extends FrameLayout implements Y {

    /* renamed from: b, reason: collision with root package name */
    public Y.a f22131b;

    public FitWindowsFrameLayout(@j.N Context context, @j.P AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    public final boolean fitSystemWindows(Rect rect) {
        Y.a aVar = this.f22131b;
        if (aVar != null) {
            aVar.a(rect);
        }
        return super.fitSystemWindows(rect);
    }

    public void setOnFitSystemWindowsListener(Y.a aVar) {
        this.f22131b = aVar;
    }
}
