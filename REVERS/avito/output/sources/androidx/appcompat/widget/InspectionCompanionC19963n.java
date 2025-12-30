package androidx.appcompat.widget;

import Ba1.C13127e;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import androidx.annotation.RestrictTo;
import com.avito.android.R;

/* compiled from: AppCompatCheckBox$InspectionCompanion.java */
@RestrictTo
@j.X
/* renamed from: androidx.appcompat.widget.n, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class InspectionCompanionC19963n implements InspectionCompanion {

    /* renamed from: a, reason: collision with root package name */
    public boolean f22575a = false;

    /* renamed from: b, reason: collision with root package name */
    public int f22576b;

    /* renamed from: c, reason: collision with root package name */
    public int f22577c;

    /* renamed from: d, reason: collision with root package name */
    public int f22578d;

    /* renamed from: e, reason: collision with root package name */
    public int f22579e;

    /* renamed from: f, reason: collision with root package name */
    public int f22580f;

    /* renamed from: g, reason: collision with root package name */
    public int f22581g;

    public final void mapProperties(@j.N PropertyMapper propertyMapper) {
        this.f22576b = propertyMapper.mapObject("backgroundTint", R.attr.backgroundTint);
        this.f22577c = propertyMapper.mapObject("backgroundTintMode", R.attr.backgroundTintMode);
        this.f22578d = propertyMapper.mapObject("buttonTint", R.attr.buttonTint);
        this.f22579e = propertyMapper.mapObject("buttonTintMode", R.attr.buttonTintMode);
        this.f22580f = propertyMapper.mapObject("drawableTint", R.attr.drawableTint);
        this.f22581g = propertyMapper.mapObject("drawableTintMode", R.attr.drawableTintMode);
        this.f22575a = true;
    }

    public final void readProperties(@j.N Object obj, @j.N PropertyReader propertyReader) {
        AppCompatCheckBox appCompatCheckBox = (AppCompatCheckBox) obj;
        if (!this.f22575a) {
            throw C13127e.o();
        }
        propertyReader.readObject(this.f22576b, appCompatCheckBox.getBackgroundTintList());
        propertyReader.readObject(this.f22577c, appCompatCheckBox.getBackgroundTintMode());
        propertyReader.readObject(this.f22578d, appCompatCheckBox.getButtonTintList());
        propertyReader.readObject(this.f22579e, appCompatCheckBox.getButtonTintMode());
        propertyReader.readObject(this.f22580f, appCompatCheckBox.getCompoundDrawableTintList());
        propertyReader.readObject(this.f22581g, appCompatCheckBox.getCompoundDrawableTintMode());
    }
}
