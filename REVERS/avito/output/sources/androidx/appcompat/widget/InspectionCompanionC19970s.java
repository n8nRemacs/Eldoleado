package androidx.appcompat.widget;

import Ba1.C13127e;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import androidx.annotation.RestrictTo;
import com.avito.android.R;

/* compiled from: AppCompatEditText$InspectionCompanion.java */
@RestrictTo
@j.X
/* renamed from: androidx.appcompat.widget.s, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class InspectionCompanionC19970s implements InspectionCompanion {

    /* renamed from: a, reason: collision with root package name */
    public boolean f22619a = false;

    /* renamed from: b, reason: collision with root package name */
    public int f22620b;

    /* renamed from: c, reason: collision with root package name */
    public int f22621c;

    /* renamed from: d, reason: collision with root package name */
    public int f22622d;

    /* renamed from: e, reason: collision with root package name */
    public int f22623e;

    public final void mapProperties(@j.N PropertyMapper propertyMapper) {
        this.f22620b = propertyMapper.mapObject("backgroundTint", R.attr.backgroundTint);
        this.f22621c = propertyMapper.mapObject("backgroundTintMode", R.attr.backgroundTintMode);
        this.f22622d = propertyMapper.mapObject("drawableTint", R.attr.drawableTint);
        this.f22623e = propertyMapper.mapObject("drawableTintMode", R.attr.drawableTintMode);
        this.f22619a = true;
    }

    public final void readProperties(@j.N Object obj, @j.N PropertyReader propertyReader) {
        AppCompatEditText appCompatEditText = (AppCompatEditText) obj;
        if (!this.f22619a) {
            throw C13127e.o();
        }
        propertyReader.readObject(this.f22620b, appCompatEditText.getBackgroundTintList());
        propertyReader.readObject(this.f22621c, appCompatEditText.getBackgroundTintMode());
        propertyReader.readObject(this.f22622d, appCompatEditText.getCompoundDrawableTintList());
        propertyReader.readObject(this.f22623e, appCompatEditText.getCompoundDrawableTintMode());
    }
}
