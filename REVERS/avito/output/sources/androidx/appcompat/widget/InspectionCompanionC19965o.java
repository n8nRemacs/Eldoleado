package androidx.appcompat.widget;

import Ba1.C13127e;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import androidx.annotation.RestrictTo;
import com.avito.android.R;

/* compiled from: AppCompatCheckedTextView$InspectionCompanion.java */
@RestrictTo
@j.X
/* renamed from: androidx.appcompat.widget.o, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class InspectionCompanionC19965o implements InspectionCompanion {

    /* renamed from: a, reason: collision with root package name */
    public boolean f22582a = false;

    /* renamed from: b, reason: collision with root package name */
    public int f22583b;

    /* renamed from: c, reason: collision with root package name */
    public int f22584c;

    /* renamed from: d, reason: collision with root package name */
    public int f22585d;

    /* renamed from: e, reason: collision with root package name */
    public int f22586e;

    /* renamed from: f, reason: collision with root package name */
    public int f22587f;

    /* renamed from: g, reason: collision with root package name */
    public int f22588g;

    public final void mapProperties(@j.N PropertyMapper propertyMapper) {
        this.f22583b = propertyMapper.mapObject("backgroundTint", R.attr.backgroundTint);
        this.f22584c = propertyMapper.mapObject("backgroundTintMode", R.attr.backgroundTintMode);
        this.f22585d = propertyMapper.mapObject("checkMarkTint", R.attr.checkMarkTint);
        this.f22586e = propertyMapper.mapObject("checkMarkTintMode", R.attr.checkMarkTintMode);
        this.f22587f = propertyMapper.mapObject("drawableTint", R.attr.drawableTint);
        this.f22588g = propertyMapper.mapObject("drawableTintMode", R.attr.drawableTintMode);
        this.f22582a = true;
    }

    public final void readProperties(@j.N Object obj, @j.N PropertyReader propertyReader) {
        AppCompatCheckedTextView appCompatCheckedTextView = (AppCompatCheckedTextView) obj;
        if (!this.f22582a) {
            throw C13127e.o();
        }
        propertyReader.readObject(this.f22583b, appCompatCheckedTextView.getBackgroundTintList());
        propertyReader.readObject(this.f22584c, appCompatCheckedTextView.getBackgroundTintMode());
        propertyReader.readObject(this.f22585d, appCompatCheckedTextView.getCheckMarkTintList());
        propertyReader.readObject(this.f22586e, appCompatCheckedTextView.getCheckMarkTintMode());
        propertyReader.readObject(this.f22587f, appCompatCheckedTextView.getCompoundDrawableTintList());
        propertyReader.readObject(this.f22588g, appCompatCheckedTextView.getCompoundDrawableTintMode());
    }
}
