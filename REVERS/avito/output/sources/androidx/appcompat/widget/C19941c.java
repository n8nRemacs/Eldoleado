package androidx.appcompat.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;

/* compiled from: ActionBarBackgroundDrawable.java */
/* renamed from: androidx.appcompat.widget.c, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C19941c extends Drawable {

    /* renamed from: a, reason: collision with root package name */
    public final ActionBarContainer f22489a;

    /* compiled from: ActionBarBackgroundDrawable.java */
    @j.X
    /* renamed from: androidx.appcompat.widget.c$a */
    public static class a {
    }

    public C19941c(ActionBarContainer actionBarContainer) {
        this.f22489a = actionBarContainer;
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(@j.N Canvas canvas) {
        ActionBarContainer actionBarContainer = this.f22489a;
        if (actionBarContainer.f21948i) {
            Drawable drawable = actionBarContainer.f21947h;
            if (drawable != null) {
                drawable.draw(canvas);
                return;
            }
            return;
        }
        Drawable drawable2 = actionBarContainer.f21945f;
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
        Drawable drawable3 = actionBarContainer.f21946g;
        if (drawable3 == null || !actionBarContainer.f21949j) {
            return;
        }
        drawable3.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    @j.X
    public final void getOutline(@j.N Outline outline) {
        ActionBarContainer actionBarContainer = this.f22489a;
        if (actionBarContainer.f21948i) {
            if (actionBarContainer.f21947h != null) {
                actionBarContainer.f21945f.getOutline(outline);
            }
        } else {
            Drawable drawable = actionBarContainer.f21945f;
            if (drawable != null) {
                drawable.getOutline(outline);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i12) {
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
    }
}
