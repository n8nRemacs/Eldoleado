package androidx.appcompat.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import j.N;

/* compiled from: DrawableWrapperCompat.java */
/* loaded from: classes.dex */
public class c extends Drawable implements Drawable.Callback {

    /* renamed from: b, reason: collision with root package name */
    public Drawable f21644b;

    @Override // android.graphics.drawable.Drawable
    public void draw(@N Canvas canvas) {
        this.f21644b.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getChangingConfigurations() {
        return this.f21644b.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable getCurrent() {
        return this.f21644b.getCurrent();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return this.f21644b.getIntrinsicHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return this.f21644b.getIntrinsicWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getMinimumHeight() {
        return this.f21644b.getMinimumHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getMinimumWidth() {
        return this.f21644b.getMinimumWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return this.f21644b.getOpacity();
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean getPadding(Rect rect) {
        return this.f21644b.getPadding(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public final int[] getState() {
        return this.f21644b.getState();
    }

    @Override // android.graphics.drawable.Drawable
    public final Region getTransparentRegion() {
        return this.f21644b.getTransparentRegion();
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void invalidateDrawable(Drawable drawable) {
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isAutoMirrored() {
        return this.f21644b.isAutoMirrored();
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isStateful() {
        return this.f21644b.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public final void jumpToCurrentState() {
        this.f21644b.jumpToCurrentState();
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        this.f21644b.setBounds(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onLevelChange(int i12) {
        return this.f21644b.setLevel(i12);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j12) {
        scheduleSelf(runnable, j12);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i12) {
        this.f21644b.setAlpha(i12);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAutoMirrored(boolean z12) {
        this.f21644b.setAutoMirrored(z12);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setChangingConfigurations(int i12) {
        this.f21644b.setChangingConfigurations(i12);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.f21644b.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setDither(boolean z12) {
        this.f21644b.setDither(z12);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setFilterBitmap(boolean z12) {
        this.f21644b.setFilterBitmap(z12);
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspot(float f12, float f13) {
        this.f21644b.setHotspot(f12, f13);
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspotBounds(int i12, int i13, int i14, int i15) {
        this.f21644b.setHotspotBounds(i12, i13, i14, i15);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setState(int[] iArr) {
        return this.f21644b.setState(iArr);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTint(int i12) {
        this.f21644b.setTint(i12);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
        this.f21644b.setTintList(colorStateList);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintMode(PorterDuff.Mode mode) {
        this.f21644b.setTintMode(mode);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z12, boolean z13) {
        return super.setVisible(z12, z13) || this.f21644b.setVisible(z12, z13);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }
}
