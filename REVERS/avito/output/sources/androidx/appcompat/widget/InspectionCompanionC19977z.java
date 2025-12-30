package androidx.appcompat.widget;

import Ba1.C13127e;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import androidx.annotation.RestrictTo;
import com.avito.android.R;

/* compiled from: AppCompatMultiAutoCompleteTextView$InspectionCompanion.java */
@RestrictTo
@j.X
/* renamed from: androidx.appcompat.widget.z, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class InspectionCompanionC19977z implements InspectionCompanion {

    /* renamed from: a, reason: collision with root package name */
    public boolean f22687a = false;

    /* renamed from: b, reason: collision with root package name */
    public int f22688b;

    /* renamed from: c, reason: collision with root package name */
    public int f22689c;

    /* renamed from: d, reason: collision with root package name */
    public int f22690d;

    /* renamed from: e, reason: collision with root package name */
    public int f22691e;

    public final void mapProperties(@j.N PropertyMapper propertyMapper) {
        this.f22688b = propertyMapper.mapObject("backgroundTint", R.attr.backgroundTint);
        this.f22689c = propertyMapper.mapObject("backgroundTintMode", R.attr.backgroundTintMode);
        this.f22690d = propertyMapper.mapObject("drawableTint", R.attr.drawableTint);
        this.f22691e = propertyMapper.mapObject("drawableTintMode", R.attr.drawableTintMode);
        this.f22687a = true;
    }

    public final void readProperties(@j.N Object obj, @j.N PropertyReader propertyReader) {
        A a12 = (A) obj;
        if (!this.f22687a) {
            throw C13127e.o();
        }
        propertyReader.readObject(this.f22688b, a12.getBackgroundTintList());
        propertyReader.readObject(this.f22689c, a12.getBackgroundTintMode());
        propertyReader.readObject(this.f22690d, a12.getCompoundDrawableTintList());
        propertyReader.readObject(this.f22691e, a12.getCompoundDrawableTintMode());
    }
}
