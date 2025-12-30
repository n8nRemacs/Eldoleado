package androidx.appcompat.widget;

import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import java.io.InputStream;

/* compiled from: ResourcesWrapper.java */
/* renamed from: androidx.appcompat.widget.n0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C19964n0 extends Resources {
    public final Drawable a(int i12) {
        return super.getDrawable(i12);
    }

    @Override // android.content.res.Resources
    public XmlResourceParser getAnimation(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public boolean getBoolean(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public int getColor(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public ColorStateList getColorStateList(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public Configuration getConfiguration() {
        throw null;
    }

    @Override // android.content.res.Resources
    public float getDimension(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public int getDimensionPixelOffset(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public int getDimensionPixelSize(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public DisplayMetrics getDisplayMetrics() {
        throw null;
    }

    @Override // android.content.res.Resources
    public Drawable getDrawable(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public Drawable getDrawableForDensity(int i12, int i13) {
        ThreadLocal<TypedValue> threadLocal = androidx.core.content.res.i.f44673a;
        throw null;
    }

    @Override // android.content.res.Resources
    public float getFraction(int i12, int i13, int i14) {
        throw null;
    }

    @Override // android.content.res.Resources
    public int getIdentifier(String str, String str2, String str3) {
        throw null;
    }

    @Override // android.content.res.Resources
    public int[] getIntArray(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public int getInteger(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public XmlResourceParser getLayout(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public Movie getMovie(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public String getQuantityString(int i12, int i13, Object... objArr) {
        throw null;
    }

    @Override // android.content.res.Resources
    public CharSequence getQuantityText(int i12, int i13) {
        throw null;
    }

    @Override // android.content.res.Resources
    public String getResourceEntryName(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public String getResourceName(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public String getResourcePackageName(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public String getResourceTypeName(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public String getString(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public String[] getStringArray(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public CharSequence getText(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public CharSequence[] getTextArray(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public void getValue(int i12, TypedValue typedValue, boolean z12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public void getValueForDensity(int i12, int i13, TypedValue typedValue, boolean z12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public XmlResourceParser getXml(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public TypedArray obtainAttributes(AttributeSet attributeSet, int[] iArr) {
        throw null;
    }

    @Override // android.content.res.Resources
    public TypedArray obtainTypedArray(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public InputStream openRawResource(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public AssetFileDescriptor openRawResourceFd(int i12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public void parseBundleExtra(String str, AttributeSet attributeSet, Bundle bundle) {
        throw null;
    }

    @Override // android.content.res.Resources
    public void parseBundleExtras(XmlResourceParser xmlResourceParser, Bundle bundle) {
        throw null;
    }

    @Override // android.content.res.Resources
    public void updateConfiguration(Configuration configuration, DisplayMetrics displayMetrics) {
        super.updateConfiguration(configuration, displayMetrics);
    }

    @Override // android.content.res.Resources
    @j.X
    public Drawable getDrawable(int i12, Resources.Theme theme) {
        ThreadLocal<TypedValue> threadLocal = androidx.core.content.res.i.f44673a;
        throw null;
    }

    @Override // android.content.res.Resources
    public String getQuantityString(int i12, int i13) {
        throw null;
    }

    @Override // android.content.res.Resources
    public String getString(int i12, Object... objArr) {
        throw null;
    }

    @Override // android.content.res.Resources
    public CharSequence getText(int i12, CharSequence charSequence) {
        throw null;
    }

    @Override // android.content.res.Resources
    public void getValue(String str, TypedValue typedValue, boolean z12) {
        throw null;
    }

    @Override // android.content.res.Resources
    public InputStream openRawResource(int i12, TypedValue typedValue) {
        throw null;
    }

    @Override // android.content.res.Resources
    @j.X
    public Drawable getDrawableForDensity(int i12, int i13, Resources.Theme theme) {
        ThreadLocal<TypedValue> threadLocal = androidx.core.content.res.i.f44673a;
        throw null;
    }
}
