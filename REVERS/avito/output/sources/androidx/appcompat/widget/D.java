package androidx.appcompat.widget;

import Ba1.C13127e;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import androidx.annotation.RestrictTo;
import com.avito.android.R;

/* compiled from: AppCompatRadioButton$InspectionCompanion.java */
@RestrictTo
@j.X
/* loaded from: classes.dex */
public final class D implements InspectionCompanion {

    /* renamed from: a, reason: collision with root package name */
    public boolean f22113a = false;

    /* renamed from: b, reason: collision with root package name */
    public int f22114b;

    /* renamed from: c, reason: collision with root package name */
    public int f22115c;

    /* renamed from: d, reason: collision with root package name */
    public int f22116d;

    /* renamed from: e, reason: collision with root package name */
    public int f22117e;

    /* renamed from: f, reason: collision with root package name */
    public int f22118f;

    /* renamed from: g, reason: collision with root package name */
    public int f22119g;

    public final void mapProperties(@j.N PropertyMapper propertyMapper) {
        this.f22114b = propertyMapper.mapObject("backgroundTint", R.attr.backgroundTint);
        this.f22115c = propertyMapper.mapObject("backgroundTintMode", R.attr.backgroundTintMode);
        this.f22116d = propertyMapper.mapObject("buttonTint", R.attr.buttonTint);
        this.f22117e = propertyMapper.mapObject("buttonTintMode", R.attr.buttonTintMode);
        this.f22118f = propertyMapper.mapObject("drawableTint", R.attr.drawableTint);
        this.f22119g = propertyMapper.mapObject("drawableTintMode", R.attr.drawableTintMode);
        this.f22113a = true;
    }

    public final void readProperties(@j.N Object obj, @j.N PropertyReader propertyReader) {
        E e12 = (E) obj;
        if (!this.f22113a) {
            throw C13127e.o();
        }
        propertyReader.readObject(this.f22114b, e12.getBackgroundTintList());
        propertyReader.readObject(this.f22115c, e12.getBackgroundTintMode());
        propertyReader.readObject(this.f22116d, e12.getButtonTintList());
        propertyReader.readObject(this.f22117e, e12.getButtonTintMode());
        propertyReader.readObject(this.f22118f, e12.getCompoundDrawableTintList());
        propertyReader.readObject(this.f22119g, e12.getCompoundDrawableTintMode());
    }
}
